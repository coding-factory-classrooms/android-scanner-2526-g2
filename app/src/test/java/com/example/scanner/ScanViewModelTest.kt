package com.example.scanner

import android.app.Application
import android.content.Context
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.test.core.app.ApplicationProvider
import app.cash.turbine.test
import com.example.scanner.scan.ScanViewModel
import io.paperdb.Paper
import junit.framework.TestCase
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock

class ScanViewModelTest {

    @Test
    fun testAmiiboFetchSuccess() = runTest {
        val testUid: String = "0467F912"

        val vm = ScanViewModel()

        vm.removeAmiibo(testUid)


        val list = Paper.book().read("amiibos", arrayListOf<Amiibo>())
        val oldSize = list?.size ?: 0

        vm.fetchAmiibo(testUid)

        val newList = Paper.book().read("amiibos", arrayListOf<Amiibo>())
        val newSize = newList?.size ?: 0

        assertTrue(newSize > oldSize)
    }

    @Test
    fun `load amiibos success`() = runTest {
        // ARRANGE
        val vm = ScanViewModel()



        vm.amiibosFLow.test {
            // ACT
            vm.loadAmiibos()

            // ASSERT
            assertEquals(
                expectMostRecentItem(),
                sampleAmiibos
            )

        }

    }
}