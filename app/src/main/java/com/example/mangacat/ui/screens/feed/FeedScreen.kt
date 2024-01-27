package com.example.mangacat.ui.screens.feed

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Image
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mangacat.data.network.Resource
import com.example.mangacat.domain.model.ChapterFeedItem
import com.example.mangacat.domain.model.MangaFeedItem
import com.example.mangacat.ui.AuthViewModel
import com.example.mangacat.ui.screens.home.HomeElements
import com.example.mangacat.ui.screens.utils.ChaptersUpdateCard
import com.example.mangacat.ui.screens.utils.TemplateScaffold
import com.example.mangacat.ui.theme.MangaCatTheme

@Composable
fun FeedScreen(
    viewModel: FeedViewModel,
    authViewModel: AuthViewModel,
    navigateBack: () -> Unit
) {
    if (authViewModel.isAuthenticated.collectAsState().value) {
        FeedContent(uiState = viewModel.uiState.collectAsState().value, navigateBack = navigateBack)
    } else {
        Text(text = "Log In to see your feed.")
    }
}

@Composable
private fun FeedContent(
    uiState: Resource<List<MangaFeedItem>>,
    navigateBack: () -> Unit
) {
    when (uiState) {
        is Resource.Loading -> {
            Text(text = "Loading...")
        }

        is Resource.Success -> {
            Success(mangaList = uiState.data, navigateBack = navigateBack)
        }

        is Resource.Error -> {
            Text(text = "Error!")
        }
    }
}

@Composable
private fun Success(
    mangaList: List<MangaFeedItem>,
    navigateBack: () -> Unit
) {
    TemplateScaffold(
        title = HomeElements.FEED.title,
        navigateBack = navigateBack
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier.padding(top = paddingValues.calculateTopPadding()),
            contentPadding = PaddingValues(5.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(mangaList) { manga ->
                ChaptersUpdateCard(manga = manga)
            }
        }
    }

}

@Preview
@Composable
fun FeedSuccessContentPreview() {
    MangaCatTheme {
        Success(
            listOf(
                MangaFeedItem(
                    mangaId = "",
                    cover = "",
                    titleManga = "Title",
                    chapterList = listOf(
                        ChapterFeedItem(
                            chapterId = "",
                            chapterTitle = "Chapter Title",
                            userNameUploader = "Username",
                            groupNameUploader = "Groupname",
                            timestamp = "2023-11-23",
                            hasSeen = false
                        )
                    )
                ),
                MangaFeedItem(
                    mangaId = "",
                    cover = "",
                    titleManga = "Title2",
                    chapterList = listOf(
                        ChapterFeedItem(
                            chapterId = "",
                            chapterTitle = "Chapter Title2",
                            userNameUploader = "Username2",
                            groupNameUploader = "Groupname2",
                            timestamp = "2023-11-24",
                            hasSeen = false
                        ),
                        ChapterFeedItem(
                            chapterId = "",
                            chapterTitle = "Chapter Title3",
                            userNameUploader = "Username2",
                            groupNameUploader = "Groupname2",
                            timestamp = "2023-11-24",
                            hasSeen = true
                        )
                    )
                )
            )
        ) {}
    }
}
