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
import com.example.mangacat.ui.screens.home.components.HorizontalPanel
import com.example.mangacat.ui.screens.home.components.SeasonalPanel

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    retryAction: () -> Unit,
    navigateToManga: (String) -> Unit,
    navigateToFeed: () -> Unit,
    navigateToStaffPicks: () -> Unit,
    navigateToRecentlyAdded: () -> Unit
) {
    HomeContent(
        homeUiState = viewModel.homeUiState,
        retryAction = retryAction,
        navigateToManga = navigateToManga,
        navigateToFeed = navigateToFeed,
        navigateToStaffPicks = navigateToStaffPicks,
        navigateToRecentlyAdded = navigateToRecentlyAdded
    )
}

@Composable
private fun HomeContent(
    homeUiState: Resource<List<HomeSeasonalMangaItem>>,
    retryAction: () -> Unit,
    navigateToManga: (String) -> Unit,
    navigateToFeed: () -> Unit,
    navigateToStaffPicks: () -> Unit,
    navigateToRecentlyAdded: () -> Unit
) {
    when (homeUiState) {
        is Resource.Loading -> LoadingScreen()
        is Resource.Success -> Success(
            homeUiState.data,
            navigateToManga,
            navigateToFeed,
            navigateToStaffPicks,
            navigateToRecentlyAdded
        )
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
private fun Success(
    mangaIdList: List<HomeSeasonalMangaItem>,
    navigateToManga: (String) -> Unit,
    navigateToFeed: () -> Unit,
    navigateToStaffPicks: () -> Unit,
    navigateToRecentlyAdded: () -> Unit
) {
    Column(
        modifier = Modifier.verticalScroll(rememberScrollState())
    ) {
        SeasonalPanel(
            mangaList = mangaIdList,
            navigateToManga = navigateToManga
        )


        HorizontalPanel(title = HomeElements.FEED.title, mangaList = mangaIdList, navigateTo = navigateToFeed)
        HorizontalPanel(title = HomeElements.STAFF.title, mangaList = mangaIdList, navigateTo = navigateToStaffPicks)
        HorizontalPanel(title = HomeElements.ADDED.title, mangaList = mangaIdList, navigateTo = navigateToRecentlyAdded)
    }
}

@Composable
fun LoadingScreen(
    modifier: Modifier = Modifier
) {
    Text(text = "Loading...", modifier = modifier)
}
