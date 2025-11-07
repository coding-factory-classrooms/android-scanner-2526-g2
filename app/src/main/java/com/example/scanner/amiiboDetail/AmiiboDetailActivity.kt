package com.example.scanner.amiiboDetail

import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.scanner.ui.theme.ScannerTheme

class AmiiboDetailActivity: ComponentActivity() {

    private lateinit var viewModel: AmiiboDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val uid = intent.getStringExtra("uid")

        //We need to create a factory to create a ViewModel with args (uid there)
        val factory = AmiiboDetailViewModelFactory(uid)
        viewModel = androidx.lifecycle.ViewModelProvider(this, factory)
            .get(AmiiboDetailViewModel::class.java)

        setContent {
            ScannerTheme {
                AmiiboDetailScreen()
            }
        }
    }
}