package ar.edu.unlam.mobile.scaffold.ui.components

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import ar.edu.unlam.mobile.scaffold.R
import ar.edu.unlam.mobile.scaffold.domain.sensor.SensorData

@SuppressLint("UnrememberedMutableState")
@Preview
@Composable
fun ParallaxBackgroundImage(
    modifier: Modifier = Modifier,
    contentDescription: String = "Parallax background image",
    painterResourceId: Int = R.drawable.pantalla_principal,
    data: SensorData? = null,
    strength: Int = 20,
    scale: Float = 1.15f
) {
    val roll by derivedStateOf { (data?.roll ?: 0f) }
    val pitch by derivedStateOf { (data?.pitch ?: 0f) }
    Box(modifier = modifier) {
        Image(
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer {
                    scaleX = scale
                    scaleY = scale
                    translationX = roll * strength
                    translationY = -pitch * strength
                }
                .align(Alignment.Center),
            painter = painterResource(id = painterResourceId),
            contentDescription = contentDescription,
            contentScale = ContentScale.FillBounds
        )
    }
}
