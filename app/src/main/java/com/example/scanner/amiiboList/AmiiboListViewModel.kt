package com.example.scanner.amiiboList

import androidx.lifecycle.ViewModel
import com.example.scanner.Amiibo
import io.paperdb.Paper
import kotlinx.coroutines.flow.MutableStateFlow

sealed class AmiiboListUiState {
    data object Loading : AmiiboListUiState()
    data class Success(val amiibos: ArrayList<Amiibo>) : AmiiboListUiState()
    data class Error(val message: String) : AmiiboListUiState()
    data object Empty : AmiiboListUiState()
}


class AmiiboListViewModel : ViewModel(){
    val uiState = MutableStateFlow<AmiiboListUiState>(AmiiboListUiState.Loading)

    fun loadAmiibos() {

        try {
           uiState.value = AmiiboListUiState.Loading
            val list = Paper.book().read("amiibos", arrayListOf<Amiibo>()) ?: arrayListOf()

            uiState.value = when {
                list.isEmpty() -> AmiiboListUiState.Empty
                else -> AmiiboListUiState.Success(list)
            }

        } catch (e: Exception) {
            uiState.value = AmiiboListUiState.Error("Erreur de chargement : ${e.message}")
        }

    }

}
