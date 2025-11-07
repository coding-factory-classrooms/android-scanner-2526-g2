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
class AmiiboDetailViewModel(private val uid: String?, private val isSimulationEnabled: Boolean = false) : ViewModel() {

    val uiState = MutableStateFlow<AmiiboDetailUiState>(AmiiboDetailUiState.Loading)
    val id = uid

    fun loadSingleAmiibo() {
        try {
            uiState.value = AmiiboDetailUiState.Loading

            val amiibo: Amiibo? = if (isSimulationEnabled) {
                com.example.scanner.sampleAmiibos.find { it.uid == uid }
            } else {
                val list = Paper.book().read("amiibos", arrayListOf<Amiibo>()) ?: arrayListOf()
                list.find { it.uid == uid }
            }

            uiState.value = AmiiboDetailUiState.Success(amiibo)

        } catch (e: Exception) {
            uiState.value = AmiiboDetailUiState.Error("Erreur de chargement : ${e.message}")
        }
    }


}


//Class that allow us to create a ViewModel with args (uid in this case)
class AmiiboDetailViewModelFactory(
    private val uid: String?,
    private val isSimulationEnabled: Boolean = false
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AmiiboDetailViewModel(uid, isSimulationEnabled) as T
    }
}