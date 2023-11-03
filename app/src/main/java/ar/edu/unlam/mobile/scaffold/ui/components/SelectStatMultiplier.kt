package ar.edu.unlam.mobile.scaffold.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp

@Preview(showBackground = true)
@Composable
fun SelectStatMultiplier(
    modifier: Modifier = Modifier,
    canMultix2BeUsed: Boolean = true,
    useMultiplierX2: (Boolean) -> Unit = {}
) {
    val offset = Offset(6.0f, 4.0f)
    var checked by rememberSaveable {
        mutableStateOf(false)
    }
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Multi x2:",
            style = TextStyle(
                fontSize = 16.sp,
                shadow = Shadow(
                    color = Color.White,
                    offset = offset,
                    blurRadius = 4f
                )
            )
        )
        Checkbox(
            checked = if (canMultix2BeUsed) checked else false,
            onCheckedChange = {
                checked = !checked
                useMultiplierX2(checked)
            }
        )
    }
}
