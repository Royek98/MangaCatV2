package com.example.mangacat.ui

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
import androidx.compose.ui.unit.dp
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
            if (currentRoute?.contains(NavigationScreens.Manga.name.substringBefore("/")) == false) {
                NavigationBar(
                    modifier = Modifier.height(100.dp)
                ) {
                    NavigationBarItem(selected = true, onClick = { /*TODO*/ }, icon = {
                        Icon(imageVector = Icons.Default.Home, contentDescription = "Home Page")
                    })
                    NavigationBarItem(selected = false, onClick = { /*TODO*/ }, icon = {
                        Icon(imageVector = Icons.Default.Search, contentDescription = "Home Page")
                    })
                    NavigationBarItem(selected = false, onClick = { /*TODO*/ }, icon = {
                        Icon(imageVector = Icons.Default.Person, contentDescription = "Home Page")
                    })
                }
            }
        }
    ) {paddingValues ->
        MangaCatNavigation(
            navController = navController,
            modifier = Modifier.padding(bottom = paddingValues.calculateBottomPadding())
        )
    }

}