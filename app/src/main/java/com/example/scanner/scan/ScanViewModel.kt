package com.example.scanner.scan

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import com.example.scanner.Amiibo
import kotlinx.coroutines.flow.MutableStateFlow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

class ScanViewModel : ViewModel() {

    interface AmiiboApi {
        @GET("{uid}")
        fun getAmiiboById(@Path("uid") uid: String): Call   <Amiibo>
    }

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://students.gryt.tech/api/L3/amiibo/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val api = retrofit.create(AmiiboApi::class.java)

    private val _amiiboState = MutableStateFlow<Amiibo?>(null)

    fun fetchAmiibo(uid: String) {
        //Allow us to use asynchronous code without blocking the UI
            val call = api.getAmiiboById(uid)

            call.enqueue(object : Callback<Amiibo>{
                override fun onResponse(
                    call: Call<Amiibo?>,
                    response: Response<Amiibo?>
                ) {
                    _amiiboState.value = response.body()
                    val amiibo = response.body()
                    Log.d("Amiibo", amiibo?.name.toString())
                }

                override fun onFailure(
                    call: Call<Amiibo?>,
                    t: Throwable
                ) {
                    _amiiboState.value = null
                    Log.e("Amiibo", "Error: failed to load db")
                }

            }
            )
    }
}
