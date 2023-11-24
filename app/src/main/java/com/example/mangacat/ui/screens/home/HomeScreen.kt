package com.example.mangacat.ui.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.mangacat.data.network.Resource
import com.example.mangacat.domain.model.HomeSeasonalMangaItem
import com.example.mangacat.ui.screens.home.components.SeasonalPanel

@Composable
fun HomeScreen(
    homeUiState: Resource<List<HomeSeasonalMangaItem>>,
    retryAction: () -> Unit
) {
    when (homeUiState) {
        is Resource.Loading -> LoadingScreen()
        is Resource.Success -> Success(homeUiState.data)

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
    mangaIdList: List<HomeSeasonalMangaItem>
) {
    Column(
        modifier = Modifier.verticalScroll(rememberScrollState())
    ) {
        SeasonalPanel(
            mangaList = mangaIdList,
        ) { mangaId ->
//            navController.navigate("${MangaCatScreens.MangaDetails.name}/$mangaId")
        }
    }
}

@Composable
fun LoadingScreen(
    modifier: Modifier = Modifier
) {
    Text(text = "Loading...", modifier = modifier)
}
