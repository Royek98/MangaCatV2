package com.example.mangacat.ui.screens.manga

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.mangacat.data.network.Resource
import com.example.mangacat.domain.model.Manga
import com.example.mangacat.ui.screens.home.ErrorScreen
import com.example.mangacat.ui.screens.home.LoadingScreen

@Composable
fun MangaScreen(
    mangaUiState: Resource<Manga>,
    retryAction: () -> Unit,
) {
    when (mangaUiState) {
        is Resource.Loading -> LoadingScreen()
        is Resource.Success -> Success(mangaUiState.data)

        is Resource.Error -> ErrorScreen(retryAction)
    }
}

@Composable
private fun Success(
    manga: Manga,
//    navigateToManga: (String) -> Unit
) {
    Column {
        Text(text = manga.id)
        Text(text = manga.title.en!!)
        manga.publicationDemographic?.let { Text(text = it.name) }
        Text(text = manga.status.name)
    }
}