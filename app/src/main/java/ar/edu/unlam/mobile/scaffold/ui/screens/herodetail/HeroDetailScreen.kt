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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ar.edu.unlam.mobile.scaffold.R
import ar.edu.unlam.mobile.scaffold.core.sensor.sensordatamanager.SensorData
import ar.edu.unlam.mobile.scaffold.domain.model.HeroModel
import ar.edu.unlam.mobile.scaffold.ui.components.CustomButton
import ar.edu.unlam.mobile.scaffold.ui.components.CustomTitle
import ar.edu.unlam.mobile.scaffold.ui.components.ParallaxBackgroundImage
import ar.edu.unlam.mobile.scaffold.ui.components.ParallaxHeroImage
import ar.edu.unlam.mobile.scaffold.ui.components.hero.HeroAppearance
import ar.edu.unlam.mobile.scaffold.ui.components.hero.HeroBiography
import ar.edu.unlam.mobile.scaffold.ui.components.hero.HeroConnections
import ar.edu.unlam.mobile.scaffold.ui.components.hero.HeroStats
import ar.edu.unlam.mobile.scaffold.ui.components.hero.HeroWork

@Composable
fun HeroDetailScreen(
    modifier: Modifier = Modifier,
    navigateToQR: () -> Unit = {},
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

    viewModel.getHero(heroID)
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()
    val hero by viewModel.hero.collectAsStateWithLifecycle()

    HeroDetailUi(
        modifier = modifier,
        sensorData = { sensorData },
        isLoading = isLoading,
        hero = { hero },
        navigateToQR = navigateToQR
    )
}

@Preview
@Composable
private fun HeroDetailUi(
    modifier: Modifier = Modifier,
    sensorData: () -> SensorData = { SensorData() },
    isLoading: Boolean = false,
    hero: () -> HeroModel = { HeroModel() },
    navigateToQR: () -> Unit = { }
) {
    Box(modifier = modifier) {
        ParallaxBackgroundImage(
            modifier = Modifier.fillMaxSize(),
            painterResourceId = R.drawable.fondo_coleccion,
            data = sensorData
        )
        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        } else {
            val scrollState = rememberScrollState()
            Column(
                modifier = Modifier
                    .verticalScroll(state = scrollState)
            ) {
                ParallaxHeroImage(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(all = 25.dp),
                    imageUrl = hero().image.url,
                    data = sensorData
                )
                CustomTitle(
                    modifier = Modifier
                        .padding(20.dp)
                        .align(alignment = Alignment.CenterHorizontally),
                    text = { "${hero().id} ${hero().name}" }
                )
                CustomButton(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .wrapContentSize()
                        .padding(8.dp),
                    onClick = navigateToQR,
                    label = { "Intercambio" }
                )
                HeroData(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth(),
                    hero = hero
                )
            }
        }
    }
}

@Composable
private fun HeroData(
    modifier: Modifier = Modifier,
    hero: () -> HeroModel = { HeroModel() }
) {
    Column(
        modifier = modifier
    ) {
        val titleTextModifier = Modifier
            .padding(20.dp)
            .align(alignment = Alignment.CenterHorizontally)
        val infoModifier = Modifier
            .fillMaxWidth()
        CustomTitle(
            modifier = titleTextModifier,
            text = { "Stats" }
        )
        HeroStats(
            modifier = infoModifier,
            stats = { hero().stats }
        )
        CustomTitle(
            modifier = titleTextModifier,
            text = { "Biografia" }
        )
        HeroBiography(
            modifier = infoModifier,
            biography = { hero().biography }
        )
        CustomTitle(
            modifier = titleTextModifier,
            text = { "Apariencia" }
        )
        HeroAppearance(
            modifier = infoModifier,
            heroAppearance = { hero().appearance }
        )
        CustomTitle(
            modifier = titleTextModifier,
            text = { "Profesion" }
        )
        HeroWork(
            modifier = infoModifier,
            heroWork = { hero().work }
        )
        CustomTitle(
            modifier = titleTextModifier,
            text = { "Conecciones" }
        )
        HeroConnections(
            modifier = infoModifier,
            heroConnections = { hero().connections }
        )
    }
}
