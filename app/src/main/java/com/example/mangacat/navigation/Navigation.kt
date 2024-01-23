package com.example.mangacat.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.mangacat.ui.AuthViewModel
import com.example.mangacat.ui.screens.feed.FeedScreen
import com.example.mangacat.ui.screens.feed.FeedViewModel
import com.example.mangacat.ui.screens.home.HomeElements
import com.example.mangacat.ui.screens.home.HomeScreen
import com.example.mangacat.ui.screens.home.HomeViewModel
import com.example.mangacat.ui.screens.library.LibraryScreen
import com.example.mangacat.ui.screens.library.LibraryViewModel
import com.example.mangacat.ui.screens.list.ListScreen
import com.example.mangacat.ui.screens.list.ListViewModel
import com.example.mangacat.ui.screens.login.LoginScreen
import com.example.mangacat.ui.screens.manga.MangaScreen
import com.example.mangacat.ui.screens.manga.MangaViewModel
import com.example.mangacat.ui.screens.manga.detailScreen.DetailScreen
import com.example.mangacat.ui.screens.read.ReadScreen
import com.example.mangacat.ui.screens.read.ReadViewModel
import com.example.mangacat.ui.screens.search.SearchScreen

enum class NavigationScreens() {
    Home, Manga, Read, Detail, Login, Search, Library, Feed, StaffPicks, RecentlyAdded
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
                viewModel = homeViewModel,
                retryAction = homeViewModel::getSeasonalManga,
                navigateToManga = { mangaId ->
                    navController.navigate("${NavigationScreens.Manga.name}/$mangaId")
                },
                navigateToFeed = {
                    navController.navigate(NavigationScreens.Feed.name)
                },
                navigateToStaffPicks = {
                    navController.navigate(NavigationScreens.StaffPicks.name)
                },
                navigateToRecentlyAdded = {
                    navController.navigate(NavigationScreens.RecentlyAdded.name)
                }
            )
        }

        composable(route = NavigationScreens.Login.name) {
            val authViewModel = it.sharedViewModel<AuthViewModel>(navController = navController)
            LoginScreen(
                authViewModel = authViewModel
            ) {
                navController.navigate(NavigationScreens.Home.name)
            }
        }

        composable(route = NavigationScreens.Search.name) {
            SearchScreen()
        }

        composable(route = NavigationScreens.Library.name) {
            val libraryViewModel = hiltViewModel<LibraryViewModel>()
            LibraryScreen(vieModel = libraryViewModel)
        }

        composable(route = NavigationScreens.Feed.name) {
            val feedFormViewModel = hiltViewModel<FeedViewModel>()
            val authViewModel = it.sharedViewModel<AuthViewModel>(navController = navController)
            FeedScreen(
                viewModel = feedFormViewModel,
                authViewModel = authViewModel,
                navigateBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(route = NavigationScreens.StaffPicks.name) {
            val listViewModel = hiltViewModel<ListViewModel>()
            listViewModel.setStaffPicks()
            ListScreen(
                viewModel = listViewModel,
                title = HomeElements.STAFF.title,
                navigateBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(route = NavigationScreens.RecentlyAdded.name) {
            val listViewModel = hiltViewModel<ListViewModel>()
            listViewModel.setRecentlyAdded()
            ListScreen(
                viewModel = listViewModel,
                title = HomeElements.ADDED.title,
                navigateBack = {
                    navController.popBackStack()
                }
            )
        }

        navigation(
            startDestination = NavigationScreens.Manga.name,
            route = "manga"
        ) {

            composable(
                route = "${NavigationScreens.Manga.name}/{mangaId}",
                arguments = listOf(
                    navArgument("mangaId") {
                        type = NavType.StringType
                    }
                )
            ) {
                val mangaId = it.arguments?.getString("mangaId") ?: ""
                val mangaViewModel =
                    it.sharedViewModel<MangaViewModel>(navController = navController)

                mangaViewModel.getManga(mangaId)
                mangaViewModel.getChapterList(mangaId)

                MangaScreen(
                    mangaId = mangaId,
                    viewModel = mangaViewModel,
                    navigateBack = { navController.popBackStack() },
                    navigateToRead = { chapterId, mangaId ->
                        navController.navigate("${NavigationScreens.Read.name}/${mangaId}/${chapterId}")
                    },
                    navigateToDetail = { navController.navigate(NavigationScreens.Detail.name) }
                )
            }

            composable(
                route = NavigationScreens.Detail.name
            ) {
                val mangaViewModel =
                    it.sharedViewModel<MangaViewModel>(navController = navController)
                DetailScreen(
                    viewModel = mangaViewModel,
                    navigateBack = { navController.popBackStack() },
                )
            }
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
                viewModel = readViewModel,
                navigateBack = { navController.popBackStack() }
            )
        }
    }
}

@Composable
inline fun <reified T : ViewModel> NavBackStackEntry.sharedViewModel(navController: NavController): T {
    val navGraphRoute = destination.parent?.route ?: return hiltViewModel()
    val parentEntry = remember(this) {
        navController.getBackStackEntry(navGraphRoute)
    }

    return hiltViewModel(parentEntry)
}