package com.example.scanner.scan

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ScanViewModel : ViewModel() {

    var isSimulationEnabled = true

    private val _scanResult = MutableStateFlow<String?>(null)
    val scanResult: StateFlow<String?> = _scanResult

    fun onScanButtonClicked() {
        val result = if (isSimulationEnabled) {
            "simulation_amiibo"
        } else {
            readFromNfc() // à implémenter dans la Story 01
        }
        _scanResult.value = result
    }

    private fun readFromNfc(): String {
        // implémentation réelle NFC (Story 01)
        return ""
    }
}
