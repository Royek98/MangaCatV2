package com.example.mangacat

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mangacat.ui.screens.home.HomeScreen
import com.example.mangacat.ui.screens.home.HomeViewModel
import com.example.mangacat.ui.theme.MangaCatTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            MangaCatTheme {
                MangaCatApp()
            }
        }
    }
}

@Composable
private fun MangaCatApp() {
    val homeViewModel = hiltViewModel<HomeViewModel>()
    HomeScreen(
        homeUiState = homeViewModel.homeUiState,
        retryAction = homeViewModel::getSeasonalManga
    )
}