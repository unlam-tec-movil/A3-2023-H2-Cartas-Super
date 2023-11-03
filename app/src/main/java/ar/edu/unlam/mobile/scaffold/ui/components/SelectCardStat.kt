package ar.edu.unlam.mobile.scaffold.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ar.edu.unlam.mobile.scaffold.domain.heroDuel.Stat

@Preview(showBackground = true)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectCardStat(
    modifier: Modifier = Modifier,
    statList: List<Stat> = listOf(
        Stat.POWER,
        Stat.DURABILITY,
        Stat.STRENGTH,
        Stat.SPEED,
        Stat.COMBAT,
        Stat.INTELLIGENCE
    ),
    onClick: (Stat) -> Unit = {}
) {
    var expanded by rememberSaveable { mutableStateOf(false) }
    var selectedStat by rememberSaveable { mutableStateOf(Stat.POWER) }

    Box(
        modifier = modifier
    ) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = {
                expanded = !expanded
            }
        ) {
            TextField(
                value = selectedStat.statName,
                onValueChange = { onClick(selectedStat) },
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = modifier.menuAnchor()
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                statList.forEach { item ->
                    DropdownMenuItem(
                        text = { Text(text = item.statName) },
                        onClick = {
                            selectedStat = item
                            onClick(selectedStat)
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}
