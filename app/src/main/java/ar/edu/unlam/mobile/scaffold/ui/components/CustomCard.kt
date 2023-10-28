package ar.edu.unlam.mobile.scaffold.ui.components

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomClickeableCard(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    content: @Composable (ColumnScope.() -> Unit) = {}
) {
    // son libres de cambiar los colores
    val cardColors = CardDefaults.elevatedCardColors(
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
        disabledContentColor = MaterialTheme.colorScheme.onSecondaryContainer
    )
    ElevatedCard(
        // enabled = hero.quantity > 0
        modifier = modifier,
        onClick = onClick,
        shape = RoundedCornerShape(4.dp),
        colors = cardColors
    ) {
        content()
    }
}

@Composable
fun CustomCard(
    modifier: Modifier = Modifier,
    content: @Composable (ColumnScope.() -> Unit) = {}
) {
    // son libres de cambiar los colores
    val cardColors = CardDefaults.elevatedCardColors(
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
        disabledContentColor = MaterialTheme.colorScheme.onSecondaryContainer
    )
    ElevatedCard(
        // enabled = hero.quantity > 0
        modifier = modifier,
        shape = RoundedCornerShape(4.dp),
        colors = cardColors
    ) {
        content()
    }
}
