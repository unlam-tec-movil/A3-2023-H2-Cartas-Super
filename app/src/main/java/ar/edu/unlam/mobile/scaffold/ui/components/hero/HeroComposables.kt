package ar.edu.unlam.mobile.scaffold.ui.components.hero

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ar.edu.unlam.mobile.scaffold.domain.hero.DataHero


//player card color = Color(0xFF16A0E8)
//adversary card color = Color(0xFFFA1404)
//color amarillo = 0xFFF9DB07
@Preview(showBackground = true)
@Composable
fun HeroPlayerCard(
    modifier: Modifier = Modifier,
    hero: DataHero = DataHero(),
    cardColors: CardColors = CardDefaults.cardColors(containerColor = Color(0xFF16A0E8))
) {
    HeroCard(
        modifier = modifier,
        hero = hero,
        cardColors = cardColors
    ) {
        HeroStats(
            modifier = Modifier.border(width = 1.dp, color = Color.Black, shape = RectangleShape),
            stats = hero.powerstats,
            brush = SolidColor(Color(0xFFF9DB07)),
            alpha = 1f,
            textColor = Color.Black
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HeroCard(
    modifier: Modifier = Modifier,
    hero: DataHero = DataHero(),
    cardColors: CardColors = CardDefaults.cardColors(containerColor = Color(0xFFFA1404)),
    content:@Composable (ColumnScope.() -> Unit) = {}
) {
    Card(
        modifier = modifier
            .border(width = 1.dp, color = Color.Black, shape = RectangleShape),
        colors = cardColors,
        shape = RectangleShape
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            HeroImage(
                modifier = Modifier
                    .size(190.dp)
                    .border(width = 1.dp, color = Color.Black),
                url = hero.image.url,
                contentScale = ContentScale.Crop
            )
            Text(
                modifier = Modifier.padding(1.dp),
                text = hero.name
            )
            content()
        }
    }
}
/*
    orden: padding.shadow, usarlos cuando llame a estos composables
 */
@Preview(showBackground = true)
@Composable
fun HeroItem(modifier: Modifier = Modifier, hero: DataHero = DataHero()) {
    val brush = Brush.horizontalGradient(
        colors = listOf(
            Color(0xFFFA1404),
            Color(0xFFF9DB07),
            Color.Yellow
        )
    )
    /*
        Sería mejor usar una textura en vez de un gradiente.
     */
    Card(
        modifier = modifier
            .background(brush = brush)
            .border(width = 2.dp, color = Color.Black, shape = RectangleShape),
        shape = RectangleShape, colors = CardDefaults.cardColors(Color(0x00000000))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(2.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            HeroImage(
                modifier = Modifier
                    .size(120.dp)
                    .padding(4.dp),
                url = hero.image.url
            )
            Spacer(
                modifier = Modifier.padding(8.dp)
            )
            Text(
                modifier = Modifier.padding(2.dp),
                text = hero.name
            )
        }
    }
}