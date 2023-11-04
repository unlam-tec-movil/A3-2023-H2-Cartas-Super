package ar.edu.unlam.mobile.scaffold.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
    var checked by rememberSaveable {
        mutableStateOf(false)
    }
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            SelectCardStat(modifier = Modifier.width(160.dp), onClick = onClickSelectedStat)
            CustomLabeledCheckbox(
                enabled = { canMultix2BeUsed },
                label = { "Multi x2:" },
                checked = { checked },
                onCheckedChange = {
                    checked = it
                    useMultiplierX2(checked)
                }
            )
        }
        Spacer(modifier = Modifier.size(8.dp))
        CustomButton(
            label = { "Pelear!" },
            onClick = onFightClick
        )
    }
}
