package com.example.scanner

import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.scanner.scan.ScanViewModel
import io.paperdb.Paper
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class ScanViewModelTest {

    @Test
    fun isAmiiboFetched() {
        val testUid: String = "0467F912"

        val vm = ScanViewModel()

        val list = Paper.book().read("amiibos", arrayListOf<Amiibo>())
        val oldSize = list?.size ?: 0

        vm.fetchAmiibo(testUid)

        val newList = Paper.book().read("amiibos", arrayListOf<Amiibo>())
        val newSize = newList?.size ?: 0

        assertTrue(newSize > oldSize)
    }

}