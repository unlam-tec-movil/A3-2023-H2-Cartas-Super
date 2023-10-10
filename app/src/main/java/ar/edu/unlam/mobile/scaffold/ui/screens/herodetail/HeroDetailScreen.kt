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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import ar.edu.unlam.mobile.scaffold.R
import ar.edu.unlam.mobile.scaffold.domain.hero.DataHero
import ar.edu.unlam.mobile.scaffold.domain.sensor.SensorData
import ar.edu.unlam.mobile.scaffold.domain.sensor.SensorDataManager
import ar.edu.unlam.mobile.scaffold.ui.components.HeroText
import ar.edu.unlam.mobile.scaffold.ui.components.ParallaxBackgroundImage
import ar.edu.unlam.mobile.scaffold.ui.components.ParallaxHeroImage
import ar.edu.unlam.mobile.scaffold.ui.components.hero.HeroAppearance
import ar.edu.unlam.mobile.scaffold.ui.components.hero.HeroBiography
import ar.edu.unlam.mobile.scaffold.ui.components.hero.HeroConnections
import ar.edu.unlam.mobile.scaffold.ui.components.hero.HeroStats
import ar.edu.unlam.mobile.scaffold.ui.components.hero.HeroWork
import ar.edu.unlam.mobile.scaffold.ui.screens.home.NavigationButton
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
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
    // Parallax begin
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    var data by remember { mutableStateOf<SensorData?>(null) }

    DisposableEffect(Unit) {
        val dataManager = SensorDataManager(context)
        dataManager.init()

        val job = scope.launch {
            dataManager.data
                .receiveAsFlow()
                .onEach { data = it }
                .collect()
        }

        onDispose {
            dataManager.cancel()
            job.cancel()
        }
    }
    // Parallax end

    val navButtonModifier = Modifier
        .wrapContentSize()
        .padding(16.dp)
    viewModel.getHero(heroID)
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()

    Box(modifier = modifier) {
        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        } else {
            val dataHero by viewModel.hero.collectAsStateWithLifecycle()
            ParallaxBackgroundImage(
                modifier = Modifier.fillMaxSize(),
                painterResourceId = R.drawable.fondo_coleccion,
                data = data
            )
            Column(
                modifier = Modifier
                    .verticalScroll(state = rememberScrollState())
            ) {
                ParallaxHeroImage(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 25.dp),
                    imageUrl = dataHero.image.url,
                    data = data
                )
                NavigationButton(
                    modifier = navButtonModifier
                        .align(Alignment.CenterHorizontally),
                    text = "Intercambio"
                ) {
                    controller.navigate(route = "qr")
                }
                // DebugSensorData(data = data)
                HeroData(modifier = Modifier.fillMaxWidth(), dataHero = dataHero)
            }
        }
    }
}

@Composable
private fun HeroData(
    modifier: Modifier = Modifier,
    dataHero: DataHero = DataHero()
) {
    val titleTextModifier = Modifier
        .padding(8.dp)
        .fillMaxWidth()
    Column(
        modifier = modifier
    ) {
        HeroText(
            modifier = titleTextModifier,
            text = "${dataHero.id} ${dataHero.name}"
        )
        HeroText(
            modifier = titleTextModifier,
            text = "Stats"
        )
        HeroStats(
            modifier = Modifier.fillMaxWidth(),
            stats = dataHero.powerstats
        )
        HeroText(
            modifier = titleTextModifier,
            text = "Biografia"
        )
        HeroBiography(
            modifier = Modifier.fillMaxWidth(),
            biography = dataHero.biography
        )
        HeroText(
            modifier = titleTextModifier,
            text = "Apariencia"
        )
        HeroAppearance(
            modifier = Modifier.fillMaxWidth(),
            heroAppearance = dataHero.appearance
        )
        HeroText(
            modifier = titleTextModifier,
            text = "Profesion"
        )
        HeroWork(
            modifier = Modifier.fillMaxWidth(),
            heroWork = dataHero.work
        )
        HeroText(
            modifier = titleTextModifier,
            text = "Conecciones"
        )
        HeroConnections(
            modifier = Modifier.fillMaxWidth(),
            heroConnections = dataHero.connections
        )
    }
}
