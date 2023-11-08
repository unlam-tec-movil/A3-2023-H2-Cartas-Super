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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import ar.edu.unlam.mobile.scaffold.R
import ar.edu.unlam.mobile.scaffold.core.sensor.sensordatamanager.SensorData
import ar.edu.unlam.mobile.scaffold.ui.components.CustomButton
import ar.edu.unlam.mobile.scaffold.ui.components.CustomProgressBarWithDots
import ar.edu.unlam.mobile.scaffold.ui.components.ParallaxBackgroundImage
import ar.edu.unlam.mobile.scaffold.ui.theme.shaka_pow

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewmodel = hiltViewModel(),
    navDuel: () -> Unit = { },
    navQuiz: () -> Unit = { },
    navMap: () -> Unit = { },
    navCollection: () -> Unit = { }
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
        modifier = modifier.testTag("home ui"),
        sensorData = { sensorData },
        navDuel = navDuel,
        navQuiz = navQuiz,
        navMap = navMap,
        navCollection = navCollection,
        cacheProgress = { cacheProgress }
    )
}

@Preview
@Composable
fun HomeUi(
    modifier: Modifier = Modifier,
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
            modifier = Modifier.testTag("background").fillMaxSize(),
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
                modifier = navButtonModifier.testTag("nav duel button"),
                onClick = navDuel,
                label = { "Duelo" }
            )

            CustomButton(
                modifier = navButtonModifier.testTag("nav quiz button"),
                onClick = navQuiz,
                label = { "Quiz" }
            )

            CustomButton(
                modifier = navButtonModifier.testTag("nav map button"),
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
        CustomProgressBarWithDots(modifier = modifier.testTag("progress bar"), progress = cacheProgress())
    } else {
        CustomButton(
            modifier = modifier.testTag("nav collection button"),
            onClick = navCollection,
            label = { "Coleccion" }
        )
    }
}
