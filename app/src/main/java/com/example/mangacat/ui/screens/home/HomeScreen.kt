package com.example.mangacat.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.mangacat.data.network.Resource
import com.example.mangacat.ui.screens.home.components.HorizontalPanel
import com.example.mangacat.ui.screens.home.components.SeasonalPanel
import com.example.mangacat.ui.screens.utils.TopAppBarBackAndAction

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
//            navigateTo = navigateToFeed,
//            navigateToManga = navigateToManga
//        )

//        when (val state = homeUiState.latestUpdate) {
//            is Resource.Loading -> LoadingScreen()
//            is Resource.Success -> {
//                LatestUpdates(state.data)
//            }
//            is Resource.Error -> {
//                ErrorScreen(messages = state.message, modifier = Modifier.padding(top = 20.dp)) {}
//            }
//        }
//
        HorizontalPanel(
            title = HomeElements.STAFF.title,
            mangaList = homeUiState.staffPicks,
            navigateTo = navigateToStaffPicks,
            navigateToManga = navigateToManga
        )
//        HorizontalPanel(
//            title = HomeElements.ADDED.title,
//            mangaList = homeUiState.recentlyAdded,
//            navigateTo = navigateToRecentlyAdded,
//            navigateToManga = navigateToManga
//        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ErrorScreen(
    modifier: Modifier = Modifier,
    messages: List<String>? = listOf(),
    retryAction: () -> Unit
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {  },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent),
                actions = {
                    IconButton(
                        onClick = {},
                        modifier = Modifier
                            .clip(
                                shape = CircleShape
                            )
                            .background(MaterialTheme.colorScheme.tertiaryContainer)
                            .height(40.dp)
                            .width(40.dp)
                    ) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "Close")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
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
}

@Composable
fun LoadingScreen(
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier.fillMaxSize()
    ) {
        CircularProgressIndicator()
    }

//    Text(text = "Loading...", modifier = modifier)
}
