package com.example.mangacat.ui.screens.home

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.mangacat.model.response.Response
import com.example.mangacat.model.cutomList.CustomListAttributes
import com.example.mangacat.model.Relationships

@Composable
fun HomeScreen(
    homeUiState: HomeUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier
) {
    when (homeUiState) {
        is HomeUiState.Loading -> LoadingScreen()
        is HomeUiState.Success -> Success(homeUiState.mangaIdList, modifier = modifier)

        is HomeUiState.Error -> ErrorScreen(retryAction)
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
    mangaIdList: Response<CustomListAttributes, Relationships>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
    ) {
        items(mangaIdList.data.relationships) {
            Text(text = "${it.id}:::${it.type.name}")
        }
    }
}

@Composable
fun LoadingScreen(
    modifier: Modifier = Modifier
) {
    Text(text = "Loading...", modifier = modifier)
}
