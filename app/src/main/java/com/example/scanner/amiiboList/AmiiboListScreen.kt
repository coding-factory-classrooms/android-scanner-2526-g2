package com.example.scanner.amiiboList

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.scanner.AmiiboHistory
import com.example.scanner.sampleAmiibos


@Composable
fun AmiiboListScreen(){
    Scaffold {innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
        ) {
            AmiiboList(sampleAmiibos)
        }

    }
}



@Composable
fun AmiiboList(amiibos: List<AmiiboHistory>) {
    LazyColumn {
        items(amiibos) { amiibo ->
            AmiiboCard( amiibo = amiibo)
        }

    }
}

@Composable
fun AmiiboCard(amiibo: AmiiboHistory) {
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
fun AmiiboListScreenPreview(){
    AmiiboListScreen()
}