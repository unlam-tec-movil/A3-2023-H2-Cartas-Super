package ar.edu.unlam.mobile.scaffold.ui.screens.herodetail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
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
import ar.edu.unlam.mobile.scaffold.domain.model.HeroModel
import ar.edu.unlam.mobile.scaffold.ui.components.CustomButton
import ar.edu.unlam.mobile.scaffold.ui.components.HeroText
import ar.edu.unlam.mobile.scaffold.ui.components.ParallaxBackgroundImage
import ar.edu.unlam.mobile.scaffold.ui.components.ParallaxHeroImage
import ar.edu.unlam.mobile.scaffold.ui.components.hero.HeroAppearance
import ar.edu.unlam.mobile.scaffold.ui.components.hero.HeroBiography
import ar.edu.unlam.mobile.scaffold.ui.components.hero.HeroConnections
import ar.edu.unlam.mobile.scaffold.ui.components.hero.HeroStats
import ar.edu.unlam.mobile.scaffold.ui.components.hero.HeroWork

/*
@Composable
private fun DebugSensorData(data: SensorData?) {
    Text(text = "Pitch: ${data?.pitch ?: 0}\nRoll: ${data?.roll ?: 0}")
}*/

@Composable
fun HeroDetailScreen(
    modifier: Modifier = Modifier,
    controller: NavHostController,
    viewModel: HeroDetailViewModelImp = hiltViewModel(),
    heroID: Int = 1
) {
    val sensorData by viewModel.sensorData
        .collectAsStateWithLifecycle(initialValue = SensorData(roll = 0f, pitch = 0f))

    DisposableEffect(Unit) {
        onDispose {
            viewModel.cancelSensorDataFlow()
        }
    }

    val navButtonModifier = Modifier
        .wrapContentSize()
        .padding(8.dp)
    viewModel.getHero(heroID)
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()

    Box(modifier = modifier) {
        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        } else {
            val hero by viewModel.hero.collectAsStateWithLifecycle()
            ParallaxBackgroundImage(
                modifier = Modifier.fillMaxSize(),
                painterResourceId = R.drawable.fondo_coleccion,
                data = { sensorData }
            )
            Column(
                modifier = Modifier
                    .verticalScroll(state = rememberScrollState())
            ) {
                ParallaxHeroImage(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(all = 25.dp),
                    imageUrl = hero.image.url,
                    data = { sensorData }
                )
                CustomButton(
                    modifier = navButtonModifier,
                    onClick = { controller.navigate(route = "qr") },
                    label = { "Intercambio" }
                )

                // DebugSensorData(data = data)
                HeroData(modifier = Modifier.fillMaxWidth(), heroModel = hero)
            }
        }
    }
}

@Composable
private fun HeroData(
    modifier: Modifier = Modifier,
    heroModel: HeroModel = HeroModel()
) {
    val titleTextModifier = Modifier
        .padding(8.dp)
        .fillMaxWidth()
    Column(
        modifier = modifier
    ) {
        HeroText(
            modifier = titleTextModifier,
            text = "${heroModel.id} ${heroModel.name}"
        )
        HeroText(
            modifier = titleTextModifier,
            text = "Stats"
        )
        HeroStats(
            modifier = Modifier.fillMaxWidth(),
            stats = heroModel.stats
        )
        HeroText(
            modifier = titleTextModifier,
            text = "Biografia"
        )
        HeroBiography(
            modifier = Modifier.fillMaxWidth(),
            biography = heroModel.biography
        )
        HeroText(
            modifier = titleTextModifier,
            text = "Apariencia"
        )
        HeroAppearance(
            modifier = Modifier.fillMaxWidth(),
            heroAppearance = heroModel.appearance
        )
        HeroText(
            modifier = titleTextModifier,
            text = "Profesion"
        )
        HeroWork(
            modifier = Modifier.fillMaxWidth(),
            heroWork = heroModel.work
        )
        HeroText(
            modifier = titleTextModifier,
            text = "Conecciones"
        )
        HeroConnections(
            modifier = Modifier.fillMaxWidth(),
            heroConnections = heroModel.connections
        )
    }
}
