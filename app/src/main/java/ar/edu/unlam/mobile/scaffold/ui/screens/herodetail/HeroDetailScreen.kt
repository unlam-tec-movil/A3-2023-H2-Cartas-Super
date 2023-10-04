package ar.edu.unlam.mobile.scaffold.ui.screens.herodetail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import ar.edu.unlam.mobile.scaffold.R
import ar.edu.unlam.mobile.scaffold.domain.hero.DataHero
import ar.edu.unlam.mobile.scaffold.ui.components.HeroText
import ar.edu.unlam.mobile.scaffold.ui.components.hero.HeroAppearance
import ar.edu.unlam.mobile.scaffold.ui.components.hero.HeroBiography
import ar.edu.unlam.mobile.scaffold.ui.components.hero.HeroConnections
import ar.edu.unlam.mobile.scaffold.ui.components.hero.HeroImage
import ar.edu.unlam.mobile.scaffold.ui.components.hero.HeroStats
import ar.edu.unlam.mobile.scaffold.ui.components.hero.HeroWork

@Composable
fun HeroDetailScreen(
    modifier: Modifier = Modifier,
    controller: NavHostController,
    viewModel: HeroDetailViewModelImp = hiltViewModel(),
    heroID: Int = 1
) {
    viewModel.getHero(heroID)
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()

    Box(modifier = modifier) {
        Image(
            painter = painterResource(id = R.drawable.fondo_coleccion),
            contentDescription = "Pantalla detalles del héroe",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.fillMaxSize()
        )
        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        } else {
            val hero by viewModel.hero.collectAsStateWithLifecycle()
            HeroDetailUi(modifier = Modifier.fillMaxSize(), dataHero = hero)
        }
    }
}

@Preview
@Composable
private fun HeroDetailUi(
    modifier: Modifier = Modifier,
    dataHero: DataHero = DataHero()
) {
    val titleTextModifier = Modifier
        .padding(8.dp)
        .fillMaxWidth()

    Column(
        modifier = modifier
            .verticalScroll(state = rememberScrollState())
    ) {
        HeroImage(
            modifier = Modifier
                .padding(8.dp)
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth(),
            url = dataHero.image.url,
            contentScale = ContentScale.FillWidth
        )
        HeroText(modifier = titleTextModifier, text = "${dataHero.id} ${dataHero.name}")
        HeroText(modifier = titleTextModifier, text = "Stats")
        HeroStats(
            Modifier.fillMaxWidth(),
            stats = dataHero.powerstats
        )
        HeroText(modifier = titleTextModifier, text = "Biografia")
        HeroBiography(modifier = Modifier.fillMaxWidth(), biography = dataHero.biography)
        HeroText(modifier = titleTextModifier, text = "Apariencia")
        HeroAppearance(
            modifier = Modifier.fillMaxWidth(),
            heroAppearance = dataHero.appearance
        )
        HeroText(modifier = titleTextModifier, text = "Profesion")
        HeroWork(modifier = Modifier.fillMaxWidth(), heroWork = dataHero.work)
        HeroText(modifier = titleTextModifier, text = "Conecciones")
        HeroConnections(
            modifier = Modifier.fillMaxWidth(),
            heroConnections = dataHero.connections
        )
    }
}
