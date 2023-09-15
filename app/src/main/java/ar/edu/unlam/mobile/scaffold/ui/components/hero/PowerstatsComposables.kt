package ar.edu.unlam.mobile.scaffold.ui.components.hero

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ar.edu.unlam.mobile.scaffold.domain.hero.Powerstats
import ar.edu.unlam.mobile.scaffold.ui.theme.shaka_pow


@Preview(showBackground = true)
@Composable
fun HeroStats(
    modifier: Modifier = Modifier,
    stats: Powerstats = Powerstats(),
    brush: Brush = SolidColor(Color.Black),
    alpha:Float = 0.8f,
    textColor: Color = Color.White
) {
    Row(
        modifier = modifier
            .background(
                brush = brush,
                alpha = alpha
            ),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)
        ) {
            StatComposable(statName = "Inteligencia", statValue = stats.intelligence, color = textColor)
            StatComposable(statName = "Velocidad", statValue = stats.speed, color = textColor)
            StatComposable(statName = "Durabilidad", statValue = stats.durability, color = textColor)
        }
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            StatComposable(statName = "Fuerza", statValue = stats.strength, color = textColor)
            StatComposable(statName = "Poder", statValue = stats.power, color = textColor)
            StatComposable(statName = "Combate", statValue = stats.combat, color = textColor)
        }
    }
}

@Composable
fun StatComposable(
    modifier: Modifier = Modifier,
    statName:String = "Inteligencia",
    statValue:String = "000",
    color: Color = Color.Black
) {
    Text(
        modifier = modifier,
        text = "$statName: $statValue",
        fontFamily = shaka_pow,
        color = color
    )
}