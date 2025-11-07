package com.example.scanner.amiiboDetail

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import com.example.scanner.ui.theme.ScannerTheme

class AmiiboDetailActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val uid = intent.getStringExtra("uid")
        val isSim = intent.getBooleanExtra("isSimulationEnabled", false)

        val factory = AmiiboDetailViewModelFactory(uid, isSim)
        val viewModel = ViewModelProvider(this, factory)[AmiiboDetailViewModel::class.java]

        setContent {
            ScannerTheme {
                AmiiboDetailScreen(uid = uid, isSimulationEnabled = isSim)
            }
        }
    }
}