package ar.edu.unlam.mobile.scaffold.ui.screens.qrscanner

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun QrScannerScreen(
    modifier: Modifier = Modifier,
    viewModel: QrScannerViewModel = hiltViewModel()
) {
    val qrString by viewModel.qrString.collectAsStateWithLifecycle()
    val startScan = viewModel::startScan

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Texto del qr: $qrString")
        Button(onClick = startScan) {
            Text(text = "Comenzar escaneo")
        }
    }
}
