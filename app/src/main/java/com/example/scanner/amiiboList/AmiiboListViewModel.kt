package com.example.scanner.amiiboList

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import com.example.scanner.Amiibo
import io.paperdb.Paper
import kotlinx.coroutines.flow.MutableStateFlow

sealed class AmiiboListUiState {
    data object Loading : AmiiboListUiState()
    data class Success(val amiibos: ArrayList<Amiibo>) : AmiiboListUiState()
    data class Error(val message: String) : AmiiboListUiState()
}

class AmiiboListViewModel : ViewModel(){
    val amiibosFlow = MutableStateFlow(listOf<Amiibo>())
    val uiState = MutableStateFlow<AmiiboListUiState>(AmiiboListUiState.Loading)


    fun loadAmiibos() {

        uiState.value = AmiiboListUiState.Loading

        uiState.value = AmiiboListUiState.Success(
            amiibos = Paper.book().read("amiibos", arrayListOf<Amiibo>())!!
        )
        amiibosFlow.value = Paper.book().read("amiibos", arrayListOf<Amiibo>())!!

    }


}