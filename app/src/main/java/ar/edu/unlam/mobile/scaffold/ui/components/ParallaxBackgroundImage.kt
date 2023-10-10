package ar.edu.unlam.mobile.scaffold.ui.components

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import ar.edu.unlam.mobile.scaffold.R
import ar.edu.unlam.mobile.scaffold.domain.sensor.SensorData

@SuppressLint("UnrememberedMutableState")
@Preview
@Composable
fun ParallaxBackgroundImage(
    modifier: Modifier = Modifier,
    contentDescription: String = "Parallax background image",
    painter: Painter = painterResource(id = R.drawable.pantalla_principal),
    data: SensorData? = null,
    scale: Float = 1.10f
) {
    val roll by derivedStateOf { (data?.roll ?: 0f) }
    val pitch by derivedStateOf { (data?.pitch ?: 0f) }

    Box(modifier = modifier) {
        Image(
            modifier = Modifier
                .offset {
                    IntOffset(
                        x = -(roll).dp.roundToPx(),
                        y = (pitch).dp.roundToPx()
                    )
                }
                .wrapContentSize(unbounded = true)
                .graphicsLayer(
                    scaleX = scale,
                    scaleY = scale
                )
                .align(Alignment.Center),
            painter = painter,
            contentDescription = contentDescription,
            contentScale = ContentScale.FillBounds
        )
    }
}
