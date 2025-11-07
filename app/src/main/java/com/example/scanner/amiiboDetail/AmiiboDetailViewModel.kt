package com.example.scanner.amiiboDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.scanner.Amiibo
import io.paperdb.Paper
import kotlinx.coroutines.flow.MutableStateFlow


sealed class AmiiboDetailUiState(){
    data object Loading : AmiiboDetailUiState()
    data class  Success(val amiibo : Amiibo?): AmiiboDetailUiState()
    data class Error(val message: String) : AmiiboDetailUiState()

}
class AmiiboDetailViewModel(uid: String?) : ViewModel() {

    val uiState = MutableStateFlow<AmiiboDetailUiState>(AmiiboDetailUiState.Loading)
    val id = uid
    fun loadSingleAmiibo(){
        try {
            uiState.value = AmiiboDetailUiState.Loading
            val list = Paper.book().read("amiibos", arrayListOf<Amiibo>()) ?: arrayListOf()
            val amiibo: Amiibo? = list.find { it.uid == id }
            uiState.value = AmiiboDetailUiState.Success(amiibo)

        }catch (e: Exception){
            uiState.value = AmiiboDetailUiState.Error("Error finding amiibo details : ${e.message}")
        }
    }

}


//Class that allow us to create a ViewModel with args (uid in this case)
class AmiiboDetailViewModelFactory(private val uid: String?) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AmiiboDetailViewModel(uid) as T
    }
}