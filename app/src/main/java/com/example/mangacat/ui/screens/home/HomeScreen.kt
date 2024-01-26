package com.example.mangacat.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mangacat.data.network.Resource
import com.example.mangacat.domain.model.HomeLatestUpdate
import com.example.mangacat.domain.model.HomeSeasonalMangaItem
import com.example.mangacat.ui.screens.home.components.HorizontalPanel
import com.example.mangacat.ui.screens.home.components.LatestUpdates
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
        homeUiState = viewModel.homeUiState.collectAsState().value,
        retryAction = retryAction,
        navigateToManga = navigateToManga,
        navigateToFeed = navigateToFeed,
        navigateToStaffPicks = navigateToStaffPicks,
        navigateToRecentlyAdded = navigateToRecentlyAdded
    )
}

@Composable
private fun HomeContent(
    homeUiState: HomeUiState,
    retryAction: () -> Unit,
    navigateToManga: (String) -> Unit,
    navigateToFeed: () -> Unit,
    navigateToStaffPicks: () -> Unit,
    navigateToRecentlyAdded: () -> Unit
) {

    Column(
        modifier = Modifier.verticalScroll(rememberScrollState())
    ) {
        when (val state = homeUiState.seasonal) {
            is Resource.Loading -> LoadingScreen()
            is Resource.Success -> {
                SeasonalPanel(
                    mangaList = state.data,
                    navigateToManga = navigateToManga
                )
            }

            is Resource.Error -> {
                ErrorScreen(messages = state.message, modifier = Modifier.padding(top = 20.dp)) {}
            }
        }

//        HorizontalPanel(
//            title = HomeElements.FEED.title,
//            mangaList = homeUiState.feed,
//            navigateTo = navigateToFeed
//        )

        when (val state = homeUiState.latestUpdate) {
            is Resource.Loading -> LoadingScreen()
            is Resource.Success -> {
                LatestUpdates(state.data)
            }
            is Resource.Error -> {
                ErrorScreen(messages = state.message, modifier = Modifier.padding(top = 20.dp)) {}
            }
        }

        HorizontalPanel(
            title = HomeElements.STAFF.title,
            mangaList = homeUiState.staffPicks,
            navigateTo = navigateToStaffPicks
        )
        HorizontalPanel(
            title = HomeElements.ADDED.title,
            mangaList = homeUiState.recentlyAdded,
            navigateTo = navigateToRecentlyAdded
        )
    }
}

@Composable
fun ErrorScreen(
    modifier: Modifier = Modifier,
    messages: List<String>? = listOf(),
    retryAction: () -> Unit
) {
    Column(
        modifier = modifier
            .background(MaterialTheme.colorScheme.onErrorContainer)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        messages?.forEach {
            Text(
                text = it,
                color = MaterialTheme.colorScheme.errorContainer,
                modifier = Modifier.padding(5.dp)
            )
        }
        Button(
            onClick = retryAction,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.onError,
                contentColor = MaterialTheme.colorScheme.error
            )
        ) {
            Text(text = "Retry")
        }
    }

}

@Composable
fun LoadingScreen(
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxWidth()
    ) {
        CircularProgressIndicator()
    }
//    Text(text = "Loading...", modifier = modifier)
}
