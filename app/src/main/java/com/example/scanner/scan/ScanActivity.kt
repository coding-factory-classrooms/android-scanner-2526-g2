package com.example.scanner.scan

import android.nfc.NfcAdapter
import android.nfc.Tag
import android.nfc.tech.MifareUltralight
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.scanner.Amiibo
import com.example.scanner.amiiboList.AmiiboListScreen
import com.example.scanner.ui.theme.ScannerTheme
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import java.util.Objects


class ScanActivity: ComponentActivity(), NfcAdapter.ReaderCallback {
    private lateinit var  nfcAdapter: NfcAdapter

    interface AmiiboApi {
        @GET("{uid}")
        fun getAmiiboById(@Path("uid")uid: String): Call<Amiibo>
    }

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

        setContent { ScannerTheme {

            AmiiboListScreen()

        } }
    }

    override fun onTagDiscovered(tag: Tag?) {

        lateinit var uid : String

        val retrofit = Retrofit.Builder()
            .baseUrl("https://students.gryt.tech/api/L3/amiibo/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val api = retrofit.create(ScanActivity.AmiiboApi::class.java)

        val ultra = MifareUltralight.get(tag)
        if (ultra != null) {
            try {
                ultra.connect()
                val page = 0

                val data = ultra.readPages(page).copyOfRange(0,4) // returns 16 bytes (4 pages)
                val uid = data.joinToString("") { "%02X".format(it) }
                Log.d("UID",uid)

                val call = api.getAmiiboById(uid)

                call.enqueue(object : Callback<Amiibo> {
                    override fun onResponse(call: Call<Amiibo>, response: Response<Amiibo>) {
                        if (response.isSuccessful) {
                            val amiibo = response.body()
                            Log.d("Amiibo", "Got Amiibo: ${amiibo?.name}")
                        } else {
                            Log.e("Amiibo", "Error: ${response.code()}")
                        }
                    }

                    override fun onFailure(
                        call: Call<Amiibo?>,
                        t: Throwable
                    ) {
                        Log.e("BDD", "ca marche pas wallah")
                    }
                }


                    )

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