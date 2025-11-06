package com.example.scanner.scan

import android.nfc.NfcAdapter
import android.nfc.Tag
import android.nfc.tech.MifareUltralight
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.scanner.R
import com.example.scanner.ui.theme.ScannerTheme


class ScanActivity: ComponentActivity(), NfcAdapter.ReaderCallback {
    private lateinit var  nfcAdapter: NfcAdapter

    override fun onCreate (savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)

        nfcAdapter = NfcAdapter.getDefaultAdapter(this)

        if (nfcAdapter == null) {
            Toast.makeText(this, "NFC not supported", Toast.LENGTH_SHORT).show()
            return
        }

        if (!nfcAdapter.isEnabled) {
            Toast.makeText(this, "Please turn on NFC", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "NFC found", Toast.LENGTH_SHORT).show()
        }

        nfcAdapter.enableReaderMode(
            this,
            this,
            NfcAdapter.FLAG_READER_NFC_A or NfcAdapter.FLAG_READER_NFC_B,
            null
        )

        setContent { ScannerTheme {} }
    }

    override fun onTagDiscovered(tag: Tag?) {
        val ultra = MifareUltralight.get(tag)
        if (ultra != null) {
            try {
                ultra.connect()
                var page = 0

                val data = ultra.readPages(page).copyOfRange(0,4) // returns 16 bytes (4 pages)
                Log.d("ULTRALIGHT", data.joinToString("") { "%02X".format(it) })
                // print each page (4 bytes)

                // readPages reads 4 pages at once

                ultra.close()
            } catch (e: Exception) {
                Log.e("ULTRALIGHT", "Ultralight read error", e)
                try { ultra.close() } catch (_: Exception) {}
            }
        } else {
            Log.d("ULTRALIGHT", "Tag is not MIFARE Ultralight")
        }


    }
}