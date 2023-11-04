package ar.edu.unlam.mobile.scaffold.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ar.edu.unlam.mobile.scaffold.domain.heroDuel.Stat

@Preview(showBackground = true)
@Composable
fun ActionMenu(
    modifier: Modifier = Modifier,
    onClickSelectedStat: (Stat) -> Unit = {},
    canMultix2BeUsed: Boolean = true,
    useMultiplierX2: (Boolean) -> Unit = {},
    onFightClick: () -> Unit = {}
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        SelectCardStat(modifier = Modifier.width(160.dp), onClick = onClickSelectedStat)
        Spacer(modifier = Modifier.size(8.dp))
        SelectStatMultiplier(
            onUseMultiplier = useMultiplierX2,
            enabled = canMultix2BeUsed
        )
        Button(
            colors = ButtonDefaults.buttonColors(Color.DarkGray),
            shape = ButtonDefaults.outlinedShape,
            onClick = {
                onFightClick()
            }
        ) {
            Text(
                text = "¡Pelear!",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                style = TextStyle(
                    fontSize = 15.sp
                )
            )
        }
    }
}
