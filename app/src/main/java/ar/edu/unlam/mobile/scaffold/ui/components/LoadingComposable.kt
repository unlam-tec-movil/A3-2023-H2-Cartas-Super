package ar.edu.unlam.mobile.scaffold.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import ar.edu.unlam.mobile.scaffold.R

@Composable
fun LoadingComposable(modifier: Modifier = Modifier) {
    Box(modifier = modifier) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(id = R.drawable.fondo_mazo_azul),
            contentDescription = "Loading Background",
            contentScale = ContentScale.FillBounds
        )
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LoadingAnimation()
        }
    }
}
