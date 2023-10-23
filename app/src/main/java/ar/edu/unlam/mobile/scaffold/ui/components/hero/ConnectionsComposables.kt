package ar.edu.unlam.mobile.scaffold.ui.components.hero

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ar.edu.unlam.mobile.scaffold.domain.model.ConnectionsModel
import ar.edu.unlam.mobile.scaffold.ui.theme.shaka_pow

@Preview(showBackground = true)
@Composable
fun HeroConnections(modifier: Modifier = Modifier, heroConnections: ConnectionsModel = ConnectionsModel()) {
    Column(
        modifier = modifier
            .background(
                brush = SolidColor(Color.Black),
                alpha = 0.8f
            ).padding(8.dp),
        horizontalAlignment = Alignment.Start,
    ) {
        Text(
            text = "Afiliacion grupal: ${heroConnections.groupAffiliation}",
            fontFamily = shaka_pow,
            color = Color.White
        )
        Text(
            text = "Personas mas cercanas: ${heroConnections.relatives}",
            fontFamily = shaka_pow,
            color = Color.White
        )
    }
}
