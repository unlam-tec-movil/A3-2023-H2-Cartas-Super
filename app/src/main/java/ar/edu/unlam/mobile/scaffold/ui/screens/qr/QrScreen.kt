package ar.edu.unlam.mobile.scaffold.ui.screens.qr

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import ar.edu.unlam.mobile.scaffold.R

@Composable
fun QrScreen(
    modifier: Modifier = Modifier,
    controller: NavHostController
){

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
            Image(
                painter = painterResource(id = R.drawable.default_imagen_heroe),
                contentDescription = "Pantalla detalles del héroe",
                contentScale = ContentScale.FillHeight,
                modifier = Modifier.fillMaxSize()
                    .padding(32.dp)
            )

            /* HeroImage(
                 modifier = Modifier
                     .padding(8.dp)
                     .align(Alignment.CenterHorizontally)
                     .fillMaxWidth(),
                 url = dataHero.image.url
             )*/
            Row(
                modifier = modifier.align(Alignment.CenterHorizontally)
            ) {
                Text(text = "SuperHeroName")
                //Text(text = "${dataHero.id} ${dataHero.name}")
            }
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
