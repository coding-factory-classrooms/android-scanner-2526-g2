package com.example.scanner.scan

import android.content.Intent
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
import java.text.SimpleDateFormat
import java.util.Calendar

class ScanViewModel : ViewModel() {

    var isSimulationEnabled = true

    private val _scanResult = MutableStateFlow<String?>(null)
    val scanResult: StateFlow<String?> = _scanResult

    private val _amiiboState = MutableStateFlow<Amiibo?>(null)
    val amiiboState: StateFlow<Amiibo?> = _amiiboState

    interface AmiiboApi {
        @GET("{uid}")
        fun getAmiiboById(@Path("uid") uid: String): Call<Amiibo>
    }

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://students.gryt.tech/api/L3/amiibo/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val api = retrofit.create(AmiiboApi::class.java)

    fun onScanButtonClicked() {
        val result = if (isSimulationEnabled) {
            "simulation_amiibo"
        } else {
            readFromNfc()
        }

        _scanResult.value = result
        if (!result.isNullOrEmpty()) {
            fetchAmiibo(result)
        }
    }

    private fun readFromNfc(): String {
        try {
            Log.d("NFC", "Lecture NFC activée via ReaderCallback")
        } catch (e: Exception) {
            Log.e("NFC", "Erreur lecture NFC : ${e.message}")
        }
        return ""
    }

    fun fetchAmiibo(uid: String) {
        val amiiboUid = uid
            val call = api.getAmiiboById(uid)
            call.enqueue(object : Callback<Amiibo>{
                override fun onResponse(
                    call: Call<Amiibo?>,
                    response: Response<Amiibo?>
                ) {

                    var amiibo: Amiibo? = response.body()
                    _amiiboState.value = amiibo
                    Log.d("DEBUG", amiibo?.name.toString())
                    val list = Paper.book().read("amiibos", arrayListOf<Amiibo>())
                    if (amiibo != null && list != null ){
                        val time = Calendar.getInstance().time
                        val timestamp = SimpleDateFormat("yyyy-MM-dd HH:mm").format(time)
                        amiibo.scannedTimestamp = timestamp
                        amiibo.uid=amiiboUid
                        list.add(amiibo)
                        Paper.book().write("amiibos", list)
                    }
                }

                override fun onFailure(call: Call<Amiibo>, t: Throwable) {
                    _amiiboState.value = null
                    Log.e("Amiibo", "Erreur API : ${t.message}", t)
                }
        })
    }
}
