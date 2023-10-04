package ar.edu.unlam.mobile.scaffold.ui.screens.qr

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import ar.edu.unlam.mobile.scaffold.R
import ar.edu.unlam.mobile.scaffold.ui.components.HeroText
import ar.edu.unlam.mobile.scaffold.ui.components.hero.HeroImage
import ar.edu.unlam.mobile.scaffold.ui.screens.herodetail.HeroDetailViewModelImp

@Composable
fun QrScreen(
    modifier: Modifier = Modifier,
    controller: NavHostController,
    viewModel: HeroDetailViewModelImp = hiltViewModel(),
    heroID: Int = 1
) {
    viewModel.getHero(heroID)
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()

    Box(modifier = modifier) {
        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        } else {
            val dataHero by viewModel.hero.collectAsStateWithLifecycle()
            val titleTextModifier = Modifier.padding(8.dp).fillMaxWidth()
            Image(
                painter = painterResource(id = R.drawable.fondo_coleccion),
                contentDescription = "Pantalla detalles del héroe",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.fillMaxSize()
            )
            Column(
                modifier = Modifier
                    .verticalScroll(state = rememberScrollState())
            ) {
                HeroImage(
                    modifier = Modifier
                        .padding(8.dp)
                        .align(Alignment.CenterHorizontally)
                        .size(300.dp),
                    url = dataHero.image.url,
                    contentScale = ContentScale.FillWidth
                )
                HeroText(modifier = titleTextModifier, text = "${dataHero.id} ${dataHero.name}")

                Image(
                    painter = painterResource(id = R.drawable.qr),
                    contentDescription = "Pantalla detalles del héroe",
                    contentScale = ContentScale.FillHeight,
                    modifier = Modifier.fillMaxSize()
                        .padding(32.dp)
                        .size(300.dp)

                )
            }

        }

    }

}