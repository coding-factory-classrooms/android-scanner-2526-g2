package com.example.scanner.scan

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import com.example.scanner.Amiibo
import io.paperdb.Paper
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

                    val amiibo: Amiibo? = response.body()
                    _amiiboState.value = amiibo
                    Log.d("Amiibo", amiibo?.name.toString())
                    val list = Paper.book().read("amiibos", arrayListOf<Amiibo>())
                    if (amiibo != null && list != null ){
                        Log.d("Debug", "Entered in the reading")
                        list.add(amiibo)
                        Paper.book().write("amiibos", list)
                    }
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
