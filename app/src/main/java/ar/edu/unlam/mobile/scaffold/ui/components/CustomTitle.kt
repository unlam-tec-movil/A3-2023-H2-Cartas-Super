package ar.edu.unlam.mobile.scaffold.ui.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Preview
@Composable
fun CustomTitle(
    modifier: Modifier = Modifier,
    text: () -> String = { "Title" }
) {
    Text(
        modifier = modifier,
        text = text(),
        style = MaterialTheme.typography.titleLarge
    )
}
