package ar.edu.unlam.mobile.scaffold.ui.screens.map

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.provider.Settings
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import ar.edu.unlam.mobile.scaffold.R
import ar.edu.unlam.mobile.scaffold.ui.components.ParallaxBackgroundImage
import ar.edu.unlam.mobile.scaffold.ui.screens.map.presentation.MapViewModel
import ar.edu.unlam.mobile.scaffold.ui.screens.map.presentation.PermissionEvent
import ar.edu.unlam.mobile.scaffold.ui.screens.map.presentation.ViewState
import ar.edu.unlam.mobile.scaffold.ui.theme.ComicWarTheme
import ar.edu.unlam.mobile.scaffold.ui.theme.shaka_pow
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState


@RequiresApi(Build.VERSION_CODES.S)
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun ScreenMap(modifier: Modifier,
              controller: NavHostController,
              viewModel: MapViewModel = hiltViewModel()
) {
      val context = LocalContext.current

    val permissionState = rememberMultiplePermissionsState(
        permissions = listOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
    )

    val viewState by viewModel.viewState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        permissionState.launchMultiplePermissionRequest()
    }

    when {
        permissionState.allPermissionsGranted -> {
            LaunchedEffect(Unit) {
                viewModel.handle(PermissionEvent.Granted)
            }
        }

        permissionState.shouldShowRationale -> {
            RationaleAlert(onDismiss = { }) {
                permissionState.launchMultiplePermissionRequest()
            }
        }

        !permissionState.allPermissionsGranted && !permissionState.shouldShowRationale -> {
            LaunchedEffect(Unit) {
                viewModel.handle(PermissionEvent.Revoked)
            }
        }
    }

    with(viewState) {
        when (this) {
            ViewState.Loading -> {
                Box(modifier = modifier,
                    contentAlignment = Alignment.Center
                ) {
                    ParallaxBackgroundImage(
                        modifier = Modifier.fillMaxSize(),
                        contentDescription = "Pantalla Coleccion",
                        painterResourceId = R.drawable.fondo_coleccion,
                    )
                    CircularProgressIndicator()
                }
            }

            ViewState.RevokedPermissions -> {
                Box(modifier = modifier,
                    contentAlignment = Alignment.Center
                ) {
                    ParallaxBackgroundImage(
                        modifier = Modifier.fillMaxSize(),
                        contentDescription = "Pantalla Coleccion",
                        painterResourceId = R.drawable.fondo_coleccion,
                    )
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(24.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("Necesitas los permisos de localizacion y Gps activado para usar el mapa",
                            style = MaterialTheme.typography.headlineLarge, textAlign = TextAlign.Center
                        )
                        Button(modifier = Modifier.padding(16.dp),
                            onClick = {
                                val intent = Intent(Settings.ACTION_SETTINGS)
                                context.startActivity(intent)
                            },
                            colors = ButtonDefaults.buttonColors(Color.Yellow)
                        ) {
                            Text("Settings",
                                fontSize = 20.sp,
                                color = Color.Black,
                                fontFamily = shaka_pow
                            )
                        }
                    }
                }

            }

            is ViewState.Success -> {
                val currentLoc =
                    LatLng(
                        this.location?.latitude ?: 0.0,
                        this.location?.longitude ?: 0.0
                    )
                val cameraState = rememberCameraPositionState()

                LaunchedEffect(key1 = currentLoc) {
                    cameraState.centerOnLocation(currentLoc)
                }

                MainScreen(
                    currentPosition = LatLng(
                        currentLoc.latitude,
                        currentLoc.longitude
                    ),
                    cameraState = cameraState
                )
            }
        }
    }
}

@Composable
fun MainScreen(currentPosition: LatLng, cameraState: CameraPositionState) {
    val marker = LatLng(currentPosition.latitude, currentPosition.longitude)
    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraState,
        properties = MapProperties(
            isMyLocationEnabled = true,
            mapType = MapType.NORMAL,

            )
    ) {
        Marker(
            state = MarkerState(position = marker),
            title = "Mi Posición Actual"

        )
        Marker(state = MarkerState(position = LatLng(-34.63333,-58.56667)),
            title = "Ramos Mejia")
        Marker(state = MarkerState(position = LatLng(-34.7,-58.58333)),
            title = "Isidro Casanova")
    }
}

@Composable
fun RationaleAlert(onDismiss: () -> Unit, onConfirm: () -> Unit) {

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties()
    ) {
        Surface(
            modifier = Modifier
                .wrapContentWidth()
                .wrapContentHeight(),
            shape = MaterialTheme.shapes.large,
            tonalElevation = AlertDialogDefaults.TonalElevation
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Para poder usar la Localizacion necesita el permiso!",

                )
                Spacer(modifier = Modifier.height(24.dp))
                TextButton(
                    onClick = {
                        onConfirm()
                        onDismiss()
                    },
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text("OK")
                }
            }
        }
    }
}

private suspend fun CameraPositionState.centerOnLocation(
    location: LatLng
) = animate(
    update = CameraUpdateFactory.newLatLngZoom(
        location,
        15f
    ),
    durationMs = 1500
)

