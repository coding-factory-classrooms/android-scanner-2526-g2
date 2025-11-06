package com.example.scanner.amiiboList

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.scanner.Amiibo
import com.example.scanner.sampleAmiibos


@Composable
fun AmiiboListScreen(viewModel: AmiiboListViewModel) {
    val uiState by viewModel.uiState.collectAsState()

    // Executed only when the key param changes
    // Unit == only once at the beginning
    LaunchedEffect(Unit) {
        println("MovieListScreen: LaunchedEffect")
        viewModel.loadAmiibos()
    }


    LaunchedEffect(uiState) {
        if (uiState is AmiiboListUiState.Success) {
            viewModel.loadAmiibos()
        }
    }

    Scaffold { innerPadding ->
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
fun AmiiboListBody(uiState: AmiiboListUiState) {
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
    LazyColumn {
        items(amiibos) { amiibo ->
            AmiiboCard(amiibo = amiibo)
        }
    }
}

@Composable
fun AmiiboCard(amiibo: Amiibo) {
    Row(
        modifier = Modifier.padding(all = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = amiibo.image,
            contentDescription = "",
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(text = amiibo.gameSeries, style = MaterialTheme.typography.titleSmall)
            Text(text = amiibo.name, style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Preview
@Composable
fun AmiiboListScreenPreview() {
    AmiiboListScreen(viewModel = AmiiboListViewModel())
}
