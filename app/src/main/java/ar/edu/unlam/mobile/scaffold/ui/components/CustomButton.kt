package ar.edu.unlam.mobile.scaffold.ui.components

import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview
@Composable
fun CustomButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    label: () -> String = { "Button" }
) {
    ElevatedButton(
        modifier = modifier.testTag(tag = "elevated button"),
        onClick = onClick,
        colors = ButtonDefaults.elevatedButtonColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 3.dp)
    ) {
        Text(
            modifier = Modifier.testTag(tag = "label"),
            text = label(),
            style = MaterialTheme.typography.labelMedium, // fontFamily = shaka_pow,
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )
    }
}
