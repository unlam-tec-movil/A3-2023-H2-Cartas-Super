package ar.edu.unlam.mobile.scaffold.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import ar.edu.unlam.mobile.scaffold.R
import ar.edu.unlam.mobile.scaffold.core.sensor.sensordatamanager.SensorData
import ar.edu.unlam.mobile.scaffold.ui.components.CustomProgressBarWithDots
import ar.edu.unlam.mobile.scaffold.ui.components.ParallaxBackgroundImage
import ar.edu.unlam.mobile.scaffold.ui.theme.shaka_pow

@Composable
fun NavigationButton(
    modifier: Modifier = Modifier,
    text: String = "button",
    onClick: () -> Unit
) {
    Button(
        onClick = {
            onClick()
        },
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(Color.Yellow)
    ) {
        Text(
            text = text,
            fontSize = 20.sp,
            color = Color.Black,
            fontFamily = shaka_pow
        )
    }
}

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewmodel = hiltViewModel(),
    controller: NavHostController
) {
    val cacheProgress by viewModel.cachingProgress.collectAsStateWithLifecycle()
    val navButtonModifier = Modifier
        .wrapContentSize()
        .padding(16.dp)

    val sensorData by viewModel.sensorData
        .collectAsStateWithLifecycle(initialValue = SensorData(0f, 0f))

    DisposableEffect(Unit) {
        onDispose {
            viewModel.cancelSensorDataFlow()
        }
    }

    Box(modifier = modifier) {
        ParallaxBackgroundImage(
            modifier = Modifier.fillMaxSize(),
            contentDescription = "Pantalla Coleccion",
            painterResourceId = R.drawable.pantalla_principal,
            data = sensorData
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ) {
            NavigationButton(
                modifier = navButtonModifier,
                text = "Duelo"
            ) {
                controller.navigate(route = "duel")
            }
            NavigationButton(
                modifier = navButtonModifier,
                text = "Quiz"
            ) {
                controller.navigate(route = "quiz")
            }
            NavigationButton(
                modifier = navButtonModifier,
                text = "Mapa"
            ) {
                controller.navigate(route = "Mapa")
            }
            NavigationButton(
                modifier = navButtonModifier,
                text = "Usuario"
            ) {
                controller.navigate(route = "Usuario")
            }


            if (cacheProgress < 1f) {
                CustomProgressBarWithDots(modifier = navButtonModifier, progress = cacheProgress)
            } else {
                NavigationButton(
                    modifier = navButtonModifier,
                    text = "Coleccion"
                ) {
                    controller.navigate(route = "collection")
                }
            }
        }
    }
}
