package com.example.scanner.scan

import android.nfc.NfcAdapter
import android.nfc.Tag
import android.nfc.tech.MifareUltralight
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.scanner.ui.theme.ScannerTheme

class ScanActivity : ComponentActivity(), NfcAdapter.ReaderCallback {

    private lateinit var nfcAdapter: NfcAdapter
    private val viewModel: ScanViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        nfcAdapter = NfcAdapter.getDefaultAdapter(this)

        nfcAdapter.enableReaderMode(
            this,
            this,
            NfcAdapter.FLAG_READER_NFC_A,
            null
        )

        setContent {
            ScannerTheme {
                ScanScreen(viewModel)
            }
        }
    }

    override fun onTagDiscovered(tag: Tag?) {
        val ultra = MifareUltralight.get(tag) ?: return

        try {
            ultra.connect()
            val data = ultra.readPages(0).copyOfRange(0, 4)
            val uid = data.joinToString("") { "%02X".format(it) }
            viewModel.fetchAmiibo(uid)
        } catch (_: Exception) {
        } finally {
            try { ultra.close() } catch (_: Exception) {}
        }
    }
}
