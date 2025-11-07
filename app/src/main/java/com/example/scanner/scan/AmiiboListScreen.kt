package com.example.scanner.scan

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import com.example.scanner.Amiibo
import com.example.scanner.amiiboDetail.AmiiboDetailActivity


@Composable
fun AmiiboListScreen(viewModel: ScanViewModel = viewModel()) {
    val uiState by viewModel.uiState.collectAsState()
    val isSimEnabled = remember { mutableStateOf(viewModel.isSimulationEnabled) }


    LaunchedEffect(true) {
        viewModel.loadAmiibos()
    }

    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Simulation",
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.width(8.dp))
                Switch(
                    checked = isSimEnabled.value,
                    onCheckedChange = { checked ->
                        isSimEnabled.value = checked
                        viewModel.isSimulationEnabled = checked
                        viewModel.loadAmiibos()
                        Log.d("SimulationMode", "Simulation activée = $checked")
                    }
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
        ) {
            AmiiboListBody(uiState)
        }
    }
}



@Composable
fun AmiiboListBody(uiState: AmiiboListUiState, ) {
    when (uiState) {
        is AmiiboListUiState.Loading ->
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator()
                Text(text = "Loading...")
            }

        is AmiiboListUiState.Success ->
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Top
            ) {
                AmiiboList(amiibos = uiState.amiibos)
            }

        is AmiiboListUiState.Empty ->
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("No Amiibos found")
            }

        is AmiiboListUiState.Error ->
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Erreur : ${uiState.message}",
                    color = MaterialTheme.colorScheme.error
                )
            }
    }
}


@Composable
fun AmiiboList(amiibos: List<Amiibo>) {
    LazyColumn(
        modifier= Modifier.fillMaxSize()
    ) {
        items(amiibos) { amiibo ->
            AmiiboCard(amiibo)
        }
    }
}

@Composable
fun AmiiboCard(amiibo: Amiibo, viewModel: ScanViewModel = viewModel()) {
    val context = LocalContext.current
    Row(
        modifier = Modifier
            .clickable(onClick = {
                val intent = Intent(context, AmiiboDetailActivity::class.java)
                intent.putExtra("uid", amiibo.uid)
                intent.putExtra("isSimulationEnabled", viewModel.isSimulationEnabled)
                context.startActivity(intent)
            })
            .fillMaxWidth()
            .padding(all = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    )  {
        AsyncImage(
            model = amiibo.image,
            contentDescription = "",
            modifier = Modifier.size(75.dp,120.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(text = amiibo.gameSeries, style = MaterialTheme.typography.titleSmall)
            Text(text = amiibo.name, style = MaterialTheme.typography.bodyMedium)
            Text(text= amiibo.scannedTimestamp.toString())
        }
        Button(onClick = { viewModel.removeAmiibo(amiibo.uid) }) { }
    }
}
fun onAmiiboClick(uid: String, context: Context) {
    Log.d("CLICK", "Clickable")
    val intent = Intent(context, AmiiboDetailActivity::class.java)
    intent.putExtra("uid", uid)
    context.startActivity(intent)
}


//@Preview
//@Composable
//fun AmiiboListScreenPreview(){
//    AmiiboListScreen(viewModel = AmiiboListViewModel())
//}
