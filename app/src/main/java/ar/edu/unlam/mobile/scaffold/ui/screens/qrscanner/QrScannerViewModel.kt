package ar.edu.unlam.mobile.scaffold.ui.screens.qrscanner

import androidx.lifecycle.ViewModel
import ar.edu.unlam.mobile.scaffold.ui.screens.HeroUiState
import com.google.mlkit.vision.codescanner.GmsBarcodeScanner
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class QrScannerViewModel @Inject constructor(private val qrScanner: GmsBarcodeScanner) : ViewModel() {

    private val _qrString = MutableStateFlow("Nothing")
    val qrString = _qrString.asStateFlow()

    private val _qrScannerUiState = MutableStateFlow<HeroUiState>(HeroUiState.Loading)
    val qrScannerUIState = _qrScannerUiState.asStateFlow()

    fun startScan() {
        _qrScannerUiState.value = HeroUiState.Loading
        qrScanner.startScan()
            .addOnSuccessListener { barcode ->
                _qrScannerUiState.value = HeroUiState.Success
                _qrString.value = barcode.rawValue!!
            }
            .addOnCanceledListener {
                _qrScannerUiState.value = HeroUiState.Cancelled
            }
            .addOnFailureListener { e ->
                _qrScannerUiState.value = HeroUiState.Error("Error al leer qr: ${e.message}")
            }
    }

    init {
        _qrScannerUiState.value = HeroUiState.Idle
    }
}
