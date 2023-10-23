package ar.edu.unlam.mobile.scaffold.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import ar.edu.unlam.mobile.scaffold.R
import ar.edu.unlam.mobile.scaffold.core.sensor.sensordatamanager.SensorData
import ar.edu.unlam.mobile.scaffold.ui.components.CustomButton
import ar.edu.unlam.mobile.scaffold.ui.components.CustomProgressBarWithDots
import ar.edu.unlam.mobile.scaffold.ui.components.ParallaxBackgroundImage

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewmodel = hiltViewModel(),
    controller: NavHostController
) {
    val cacheProgress by viewModel.cachingProgress.collectAsStateWithLifecycle()

    val sensorData by viewModel.sensorData
        .collectAsStateWithLifecycle(initialValue = SensorData(0f, 0f))

    DisposableEffect(Unit) {
        onDispose {
            viewModel.cancelSensorDataFlow()
        }
    }

    HomeUi(
        modifier = modifier,
        sensorData = { sensorData },
        navDuel = { controller.navigate(route = "duel") },
        navQuiz = { controller.navigate(route = "quiz") },
        navMap = { controller.navigate(route = "Mapa") },
        navCollection = { controller.navigate(route = "collection") },
        cacheProgress = { cacheProgress }
    )
}

@Composable
private fun HomeUi(
    modifier: Modifier,
    sensorData: () -> SensorData = { SensorData() },
    navDuel: () -> Unit = { },
    navQuiz: () -> Unit = { },
    navMap: () -> Unit = { },
    navCollection: () -> Unit = { },
    cacheProgress: () -> Float = { 0f }
) {
    val navButtonModifier = Modifier
        .wrapContentSize()
        .padding(8.dp)

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
            CustomButton(
                modifier = navButtonModifier,
                onClick = navDuel,
                label = { "Duelo" }
            )

            CustomButton(
                modifier = navButtonModifier,
                onClick = navQuiz,
                label = { "Quiz" }
            )

            CustomButton(
                modifier = navButtonModifier,
                onClick = navMap,
                label = { "Mapa" }
            )

            CollectionButton(
                modifier = navButtonModifier,
                cacheProgress = cacheProgress,
                navCollection = navCollection
            )
        }
    }
}

@Composable
private fun CollectionButton(
    modifier: Modifier = Modifier,
    cacheProgress: () -> Float = { 0f },
    navCollection: () -> Unit = { }
) {
    if (cacheProgress() < 1f) {
        CustomProgressBarWithDots(modifier = modifier, progress = cacheProgress())
    } else {
        CustomButton(
            modifier = modifier,
            onClick = navCollection,
            label = { "Coleccion" }
        )
    }
}
