package com.example.mangacat.ui

import android.util.Log
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.mangacat.navigation.MangaCatNavigation
import com.example.mangacat.navigation.NavigationScreens

@Composable
fun MangaCatApp() {

    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route
            if (
                currentRoute?.contains(NavigationScreens.Home.name.substringBefore("/")) == true
                || currentRoute?.contains(NavigationScreens.Login.name.substringBefore("/")) == true
                || currentRoute?.contains(NavigationScreens.Search.name.substringBefore("/")) == true
            ) {
                val items = listOf(
                    BottomNavigationItem(
                        route = NavigationScreens.Home,
                        icon = Icons.Default.Home,
                        routeDescription = "Home Page"
                    ),
                    BottomNavigationItem(
                        route = NavigationScreens.Search,
                        icon = Icons.Default.Search,
                        routeDescription = "Search Page"
                    ),
                    BottomNavigationItem(
                        route = NavigationScreens.Login,
                        icon = Icons.Default.Person,
                        routeDescription = "Login Page"
                    )
                )
                NavigationBar(
                    modifier = Modifier.height(100.dp),
//                    containerColor = MaterialTheme.colorScheme.secondaryContainer
                ) {
//                    NavigationBarItem(selected = true, onClick = { navController.navigate(NavigationScreens.Home.name) }, icon = {
//                        Icon(imageVector = Icons.Default.Home, contentDescription = "Home Page")
//                    })
//                    NavigationBarItem(selected = false, onClick = { /*TODO*/ }, icon = {
//                        Icon(imageVector = Icons.Default.Search, contentDescription = "Search Page")
//                    })
//                    NavigationBarItem(selected = false, onClick = { navController.navigate(NavigationScreens.Login.name) }, icon = {
//                        Icon(imageVector = Icons.Default.Person, contentDescription = "Login Page")
//                    })
                    items.forEach { screen ->
                        NavigationBarItem(
                            selected = currentRoute.contains(screen.route.name),
                            onClick = {
                                navController.navigate(screen.route.name) {
                                    // Pop up to the start destination of the graph to
                                    // avoid building up a large stack of destinations
                                    // on the back stack as users select items
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    // Avoid multiple copies of the same destination when
                                    // reselecting the same item
                                    launchSingleTop = true
                                    // Restore state when reselecting a previously selected item
                                    restoreState = true
                                }
                            },
                            icon = {
                                Icon(
                                    imageVector = screen.icon,
                                    contentDescription = screen.routeDescription
                                )
                            }
                        )
                    }
                }
            }
        }
    ) { paddingValues ->
        MangaCatNavigation(
            navController = navController,
            modifier = Modifier.padding(bottom = paddingValues.calculateBottomPadding())
        )
    }

}

private data class BottomNavigationItem(
    val route: NavigationScreens,
    val icon: ImageVector,
    val routeDescription: String
)