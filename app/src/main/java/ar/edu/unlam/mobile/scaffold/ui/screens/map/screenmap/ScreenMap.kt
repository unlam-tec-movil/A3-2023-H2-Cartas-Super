package ar.edu.unlam.mobile.scaffold.ui.screens.map.screenmap

import android.Manifest
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import ar.edu.unlam.mobile.scaffold.ui.theme.ComicWarTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.android.gms.location.FusedLocationProviderClient


@Composable
fun ScreenMap(
    fusedLocationProviderClient: FusedLocationProviderClient,
    modifier: Modifier,
    controller: NavHostController
){
    MainScreen(fusedLocationProviderClient = fusedLocationProviderClient)
}




@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun MainScreen(
    fusedLocationProviderClient: FusedLocationProviderClient,
    useSystemUIController: Boolean = true) {

    val permissionState = rememberPermissionState(Manifest.permission.ACCESS_FINE_LOCATION)

    if(permissionState.status.isGranted){
        ComicWarTheme(useSystemUIController = useSystemUIController) {
            MapScreen(fusedLocationProviderClient)
        }
    }
    else {
        LaunchedEffect(true){
            permissionState.launchPermissionRequest()
        }
    }


}