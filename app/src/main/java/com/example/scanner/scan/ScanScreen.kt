package com.example.scanner.scan

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun ScanScreen(viewModel: ScanViewModel = viewModel()) {
    val scanResult by viewModel.scanResult.collectAsState()

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
    }
}

@Preview(showBackground = true)
@Composable
fun ScanScreenPreview() {

    ScanScreen()
}
