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
import ar.edu.unlam.mobile.scaffold.domain.model.WorkModel
import ar.edu.unlam.mobile.scaffold.ui.theme.shaka_pow

@Preview(showBackground = true)
@Composable
fun HeroWork(modifier: Modifier = Modifier, heroWork: WorkModel = WorkModel()) {
    Column(
        modifier = modifier
            .background(
                brush = SolidColor(Color.Black),
                alpha = 0.8f
            ).padding(8.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "Profesion: ${heroWork.occupation}",
            fontFamily = shaka_pow,
            color = Color.White
        )
        Text(
            text = "Base: ${heroWork.base}",
            fontFamily = shaka_pow,
            color = Color.White
        )
    }
}
