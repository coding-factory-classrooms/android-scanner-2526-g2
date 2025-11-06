package com.example.scanner.scan

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.scanner.ui.theme.ScannerTheme

@Composable
fun ScanScreen(viewModel: ScanViewModel = viewModel()) {
    val scanResult by viewModel.scanResult.collectAsState()
    val amiibo by viewModel.amiiboState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = { viewModel.onScanButtonClicked() }) {
            Text("Scanner un Amiibo")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = scanResult ?: "Aucun scan effectué",
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (amiibo != null) {
            Text("Nom : ${amiibo!!.name}", style = MaterialTheme.typography.bodyLarge)
            Text("Série : ${amiibo!!.gameSeries}", style = MaterialTheme.typography.bodyMedium)
        } else if (scanResult != null) {
            Text("Chargement des données Amiibo...", style = MaterialTheme.typography.bodySmall)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ScanScreenPreview() {
    ScannerTheme {
        ScanScreen()
    }
}
