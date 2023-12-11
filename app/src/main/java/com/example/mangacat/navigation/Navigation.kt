package com.example.mangacat.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.mangacat.ui.screens.home.HomeScreen
import com.example.mangacat.ui.screens.home.HomeViewModel
import com.example.mangacat.ui.screens.manga.MangaScreen
import com.example.mangacat.ui.screens.manga.MangaViewModel
import com.example.mangacat.ui.screens.read.ReadScreen
import com.example.mangacat.ui.screens.read.ReadViewModel

enum class NavigationScreens() {
    Home, Manga, Read
}

@Composable
fun MangaCatNavigation(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = NavigationScreens.Home.name,
        modifier = modifier
    ) {
        composable(route = NavigationScreens.Home.name) {
            val homeViewModel = hiltViewModel<HomeViewModel>()

            HomeScreen(
                homeUiState = homeViewModel.homeUiState,
                retryAction = homeViewModel::getSeasonalManga,
                navigateToManga = { mangaId ->
                    navController.navigate("${NavigationScreens.Manga.name}/$mangaId")
                }
            )
        }

        composable(
            route = "${NavigationScreens.Manga.name}/{mangaId}",
            arguments = listOf(
                navArgument("mangaId") {
                    type = NavType.StringType
                }
            )
        ) {
            val mangaId = it.arguments?.getString("mangaId") ?: ""
            val mangaViewModel = hiltViewModel<MangaViewModel>()

            mangaViewModel.setMangaId(mangaId)
            mangaViewModel.getManga()
            mangaViewModel.getChapterList()

            MangaScreen(
                mangaUiState = mangaViewModel.mangaUiState,
                chapterListUiState = mangaViewModel.chapterListUiState,
                retryManga = mangaViewModel::getManga,
                retryChapterList = mangaViewModel::getChapterList,
                navigateBack = { navController.popBackStack() },
                navigateToRead = { chapterId, mangaId ->
                    navController.navigate("${NavigationScreens.Read.name}/${mangaId}/${chapterId}")
                }
            )
        }

        composable(
            route = "${NavigationScreens.Read.name}/{mangaId}/{chapterId}",
            arguments = listOf(
                navArgument("mangaId") {
                    type = NavType.StringType
                },
                navArgument("chapterId") {
                    type = NavType.StringType
                }
            )
        ) {
            // todo delete mangaId if there is no need for it
            val mangaId = it.arguments?.getString("mangaId") ?: ""
            val chapterId = it.arguments?.getString("chapterId") ?: ""

            val readViewModel = hiltViewModel<ReadViewModel>()

            readViewModel.setChapterId(chapterId)
            readViewModel.getReadPages()

            ReadScreen(
                readUiState = readViewModel.readUiState,
                showBar = readViewModel.showBar,
                showBarChangeState = readViewModel::showBarChangeState,
                retryRead = readViewModel::getReadPages,
                navigateBack = { navController.popBackStack() }
            )
        }
    }
}