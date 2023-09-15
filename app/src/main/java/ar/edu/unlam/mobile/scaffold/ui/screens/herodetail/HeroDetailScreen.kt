package ar.edu.unlam.mobile.scaffold.ui.screens.herodetail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import ar.edu.unlam.mobile.scaffold.R
import ar.edu.unlam.mobile.scaffold.ui.components.hero.HeroAppearance
import ar.edu.unlam.mobile.scaffold.ui.components.hero.HeroBiography
import ar.edu.unlam.mobile.scaffold.ui.components.hero.HeroConnections
import ar.edu.unlam.mobile.scaffold.ui.components.hero.HeroImage
import ar.edu.unlam.mobile.scaffold.ui.components.hero.HeroStats
import ar.edu.unlam.mobile.scaffold.ui.components.hero.HeroWork
import ar.edu.unlam.mobile.scaffold.ui.theme.shaka_pow


/* ////////////////////////////////////////////////////////////////////////////////////////////////
    REFACTORIZAR BOTONES PARA SIMPLIFICAR CÓDIGO //////////////////////////////////////////////////
 */////////////////////////////////////////////////////////////////////////////////////////////////



@Composable
fun HeroDetailScreen(
    modifier: Modifier = Modifier,
    controller: NavHostController,
    viewModel: HeroDetailViewModelImp = hiltViewModel(),
    heroID: Int = 1
) {
    viewModel.getHero(heroID)
    var isStatsVisible by rememberSaveable { mutableStateOf(true) }
    var isBiographyVisible by rememberSaveable { mutableStateOf(true) }
    var isAppearanceVisible by rememberSaveable { mutableStateOf(true) }
    var isWorkVisible by rememberSaveable { mutableStateOf(true) }
    var isConnectionsVisible by rememberSaveable { mutableStateOf(true) }

    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()

    if(isLoading) {
        CircularProgressIndicator(modifier = Modifier.fillMaxSize())
    }else {
        val dataHero by viewModel.hero.collectAsStateWithLifecycle()

        Box(modifier = modifier) {
            Image(
                painter = painterResource(id = R.drawable.fondo_coleccion),
                contentDescription = "Pantalla detalles del héroe",
                contentScale = ContentScale.FillHeight,
                modifier = Modifier.fillMaxSize()
            )
            Column(
                modifier = modifier
                    .verticalScroll(state = rememberScrollState())
            ) {

                HeroImage(modifier = Modifier
                    .padding(8.dp)
                    .align(Alignment.CenterHorizontally)
                    .fillMaxWidth(),
                    url = dataHero.image.url
                )
                Row(
                    modifier = modifier.align(Alignment.CenterHorizontally)
                ) {
                    Text(text = "${dataHero.id} ${dataHero.name}")
                }
                Button(
                    onClick = { isStatsVisible = !isStatsVisible },
                    modifier = modifier
                        .align(Alignment.CenterHorizontally)
                        .fillMaxWidth()
                        .padding(16.dp),
                    colors = ButtonDefaults.buttonColors(Color.Red)
                ) {
                    Text(
                        text = if (isStatsVisible) "Ocultar Stats" else "Mostrar Stats",
                        fontSize = 20.sp,
                        color = Color.White,
                        fontFamily = shaka_pow
                    )
                }
                if (isStatsVisible) {
                    HeroStats(
                        Modifier.fillMaxWidth(),
                        stats = dataHero.powerstats
                    )
                }

                Button(
                    onClick = { isBiographyVisible = !isBiographyVisible },
                    modifier = modifier
                        .align(Alignment.CenterHorizontally)
                        .fillMaxWidth()
                        .padding(16.dp),
                    colors = ButtonDefaults.buttonColors(Color.Red)
                ) {
                    Text(
                        text = if (isBiographyVisible) "Ocultar biografia" else "Mostrar biografia",
                        fontSize = 20.sp,
                        color = Color.White,
                        fontFamily = shaka_pow
                    )
                }
                if (isBiographyVisible) {
                    HeroBiography(modifier = Modifier.fillMaxWidth(),biography = dataHero.biography)
                }

                Button(
                    onClick = { isAppearanceVisible = !isAppearanceVisible },
                    modifier = modifier
                        .align(Alignment.CenterHorizontally)
                        .fillMaxWidth()
                        .padding(16.dp),
                    colors = ButtonDefaults.buttonColors(Color.Red)
                ) {
                    Text(
                        text = if (isAppearanceVisible) "Ocultar apariencia" else "mostrar apariencia",
                        fontSize = 20.sp,
                        color = Color.White,
                        fontFamily = shaka_pow
                    )
                }
                if (isAppearanceVisible) {
                    HeroAppearance(modifier = Modifier.fillMaxWidth(),heroAppearance = dataHero.appearance)
                }

                Button(
                    onClick = { isWorkVisible = !isWorkVisible },
                    modifier = modifier
                        .align(Alignment.CenterHorizontally)
                        .fillMaxWidth()
                        .padding(16.dp),
                    colors = ButtonDefaults.buttonColors(Color.Red)
                ) {
                    Text(
                        text = if (isWorkVisible) "Ocultar profesion" else "Mostrar profesi0n",
                        fontSize = 20.sp,
                        color = Color.White,
                        fontFamily = shaka_pow
                    )
                }
                if (isWorkVisible) {
                    HeroWork(modifier = Modifier.fillMaxWidth(),heroWork = dataHero.work)
                }
                Button(
                    onClick = { isConnectionsVisible = !isConnectionsVisible },
                    modifier = modifier
                        .align(Alignment.CenterHorizontally)
                        .fillMaxWidth()
                        .padding(16.dp),
                    colors = ButtonDefaults.buttonColors(Color.Red)
                ) {
                    Text(
                        text = if (isConnectionsVisible) "Ocultar connecciones" else "Mostrar conecciones",
                        fontSize = 20.sp,
                        color = Color.White,
                        fontFamily = shaka_pow
                    )
                }
                if (isConnectionsVisible) {
                    HeroConnections(modifier = Modifier.fillMaxWidth(),heroConnections = dataHero.connections)
                }
            }
        }
    }


}
