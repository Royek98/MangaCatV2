package com.example.mangacat.ui.screens.home

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.mangacat.data.network.Resource

@Composable
fun HomeScreen(
    homeUiState: Resource<List<String>>,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier
) {
    when (homeUiState) {
        is Resource.Loading -> LoadingScreen()
        is Resource.Success -> Success(homeUiState.data, modifier = modifier)

        is Resource.Error -> ErrorScreen(retryAction)
    }
}

@Composable
fun ErrorScreen(
    retryAction: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface {
        Text(text = "Error", modifier = modifier)
        Button(onClick = retryAction) {
            Text(text = "Retry")
        }
    }
}

@Composable
fun Success(
    mangaIdList: List<String>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
    ) {
        items(mangaIdList) {
//            Text(text = "${it.id}:::${it.cover}:::${it.tags}")
        }
    }
}

@Composable
fun LoadingScreen(
    modifier: Modifier = Modifier
) {
    Text(text = "Loading...", modifier = modifier)
}
