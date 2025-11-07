package com.example.scanner.amiiboList

import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.scanner.ui.theme.ScannerTheme

class AmiiboListActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContent {
            ScannerTheme{
                AmiiboListScreen()
            }
        }
    }
}