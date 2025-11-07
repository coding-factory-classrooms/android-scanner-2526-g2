package com.example.scanner.amiiboDetail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import com.example.scanner.Amiibo


@Composable
fun AmiiboDetailScreen(
    uid: String?,
    isSimulationEnabled: Boolean = false
) {
    val viewModel: AmiiboDetailViewModel = viewModel(
        factory = AmiiboDetailViewModelFactory(uid, isSimulationEnabled)
    )

    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadSingleAmiibo()
    }

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
        ) {
            AmiiboDetailBody(uiState)
        }
    }
}

@Composable
fun AmiiboDetailBody(uiState: AmiiboDetailUiState) {
    when(uiState) {
        is AmiiboDetailUiState.Loading -> Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator()
            Text(text = "Loading...")
        }

        is AmiiboDetailUiState.Success -> Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top
        ) {
            AmiiboDetail(amiibo = uiState.amiibo)
        }
        is AmiiboDetailUiState.Error -> Box(
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
fun AmiiboDetail(amiibo: Amiibo?) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement= Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = amiibo?.image,
            contentDescription = "",
            modifier = Modifier.size(200.dp,300.dp)
        )
        Text(text = "Amiibo Name: " + amiibo?.name.toString(), style = MaterialTheme.typography.bodyMedium)
        Text(text = "Game Series: " + amiibo?.gameSeries.toString(), style = MaterialTheme.typography.titleSmall)
        Text(text = "Character: " + amiibo?.character.toString(), style = MaterialTheme.typography.titleSmall)
        Text(text = "Game Series: " + amiibo?.gameSeries.toString(), style = MaterialTheme.typography.titleSmall)
        Text(text = "Release Date: " +amiibo?.release.toString(), style = MaterialTheme.typography.titleSmall)
    }
}