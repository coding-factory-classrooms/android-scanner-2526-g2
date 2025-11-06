package com.example.scanner.scan

import android.nfc.NfcAdapter
import android.nfc.Tag
import android.nfc.tech.MifareUltralight
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.scanner.Amiibo
import io.paperdb.Paper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

class ScanViewModel : ViewModel() {

    // --- Simulation flag ---
    var isSimulationEnabled = true

    // --- Flux pour le code scanné ---
    private val _scanResult = MutableStateFlow<String?>(null)
    val scanResult: StateFlow<String?> = _scanResult

    // --- Flux pour l’Amiibo récupéré depuis l’API ---
    private val _amiiboState = MutableStateFlow<Amiibo?>(null)
    val amiiboState: StateFlow<Amiibo?> = _amiiboState

    // --- Retrofit / API ---
    interface AmiiboApi {
        @GET("{uid}")
        fun getAmiiboById(@Path("uid") uid: String): Call<Amiibo>
    }

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://students.gryt.tech/api/L3/amiibo/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val api = retrofit.create(AmiiboApi::class.java)

    // --- Bouton “Scanner un Amiibo” ---
    fun onScanButtonClicked() {
        val result = if (isSimulationEnabled) {
            // Mode simulation
            "simulation_amiibo"
        } else {
            // Lecture NFC réelle
            readFromNfc()
        }

        _scanResult.value = result
        if (!result.isNullOrEmpty()) {
            fetchAmiibo(result)
        }
    }

    // --- Lecture réelle NFC (Story 01) ---
    private fun readFromNfc(): String {
        try {
            // On utilise la lecture fournie par NfcAdapter via ScanActivity.onTagDiscovered
            // (donc ici, on ne fait rien directement)
            // Cette fonction sert juste de placeholder logique.
            Log.d("NFC", "Lecture NFC activée via ReaderCallback")
        } catch (e: Exception) {
            Log.e("NFC", "Erreur lecture NFC : ${e.message}")
        }
        return "" // Le vrai UID vient du callback onTagDiscovered
    }

    // --- Appel API + sauvegarde PaperDB ---
    fun fetchAmiibo(uid: String) {
        val call = api.getAmiiboById(uid)

        call.enqueue(object : Callback<Amiibo> {
            override fun onResponse(call: Call<Amiibo>, response: Response<Amiibo>) {
                val amiibo = response.body()
                _amiiboState.value = amiibo

                if (amiibo != null) {
                    val list = Paper.book().read("amiibos", arrayListOf<Amiibo>())
                    list?.add(amiibo)
                    Paper.book().write("amiibos", list)
                    Log.d("Amiibo", "Amiibo ajouté : ${amiibo.name}")
                } else {
                    Log.w("Amiibo", "Réponse vide pour UID : $uid")
                }
            }

            override fun onFailure(call: Call<Amiibo>, t: Throwable) {
                _amiiboState.value = null
                Log.e("Amiibo", "Erreur API : ${t.message}", t)
            }
        })
    }
}
