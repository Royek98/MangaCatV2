package com.example.mangacat.ui.screens.library

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mangacat.data.network.Resource
import com.example.mangacat.ui.screens.home.LoadingScreen
import com.example.mangacat.ui.theme.MangaCatTheme
import com.example.mangacat.utils.toTitle
import kotlinx.coroutines.launch

@Composable
fun LibraryScreen(vieModel: LibraryViewModel) {
    LibraryContent(
        libraryUiState = vieModel.libraryUiState.collectAsState().value,
        changeTab = vieModel::changeTab,
        getReading = vieModel::getReading
    )
}

@Composable
private fun LibraryContent(
    libraryUiState: LibraryUiState,
    changeTab: (Int) -> Unit,
    getReading: (List<String>) -> Unit
) {
    Scaffold { paddingValues ->
        when (val statusList = libraryUiState.statusList) {
            is Resource.Loading -> {
                LoadingScreen()
            }

            is Resource.Success -> {
                Success(
                    data = statusList.data,
                    uiState = libraryUiState,
                    paddingValues = paddingValues,
                    currentTab = libraryUiState.currentTab,
                    changeTab = changeTab,
                    getReading = getReading
                )
            }

            is Resource.Error -> {}
        }
    }

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun Success(
    data: Map<String, List<String>>,
    uiState: LibraryUiState,
    paddingValues: PaddingValues,
    currentTab: Int,
    changeTab: (Int) -> Unit,
    getReading: (List<String>) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        val pagerState = rememberPagerState(pageCount = {
            data.size
        })
        val coroutineScope = rememberCoroutineScope()
        val keys = data.keys.map { it }

        LaunchedEffect(pagerState) {
            snapshotFlow { pagerState.currentPage }.collect { page ->
                changeTab(page)
                when (keys[page]) {
                    "reading" -> data[keys[page]]?.let { getReading(it) }
                }
            }
        }

        Indicator(currentPage = currentTab, keys = keys, changeTab = changeTab) { index ->
            coroutineScope.launch {
                pagerState.scrollToPage(index)
            }
        }

        HorizontalPager(
            state = pagerState,
            verticalAlignment = Alignment.Top
        ) { pageIndex ->
            // todo replace this (create fields in viemodel for every status type)
            when (keys[pageIndex]) {
                "reading" -> {
                    when (val reading = uiState.reading) {
                        is Resource.Loading -> {
                            LoadingScreen()
                        }

                        is Resource.Success -> {
                            Column {
                                reading.data.forEach {
                                    it.title.en?.let { title ->
                                        Text(text = title)
                                    }
                                }
                            }
                        }

                        is Resource.Error -> {}
                    }
                }

                "completed" -> {
                    Text(text = "${data[keys[1]]}")
                }
            }
        }
    }
}

@Composable
private fun Indicator(
    currentPage: Int,
    keys: List<String>,
    changeTab: (Int) -> Unit,
    jumpToIndex: (Int) -> Unit
) {
    ScrollableTabRow(
        selectedTabIndex = currentPage,
        modifier = Modifier.padding(5.dp)
    ) {
        keys.forEachIndexed { index, key ->
            Tab(
                selected = index == currentPage,
                modifier = Modifier.padding(5.dp),
                onClick = {
                    changeTab(index)
                    jumpToIndex(index)
                }
            ) {
                Text(text = key.toTitle())
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SuccessPreview() {
    MangaCatTheme {
        Success(
            data = linkedMapOf(
                "Reading" to listOf("mangaId1", "id2"),
                "Completed" to listOf("id3"),
                "Dropped" to listOf("id4"),
                "On hold" to listOf("id4"),
                "Plan to read" to listOf("id4"),
                "Re-reading" to listOf("id4"),
            ),
            paddingValues = PaddingValues(),
            uiState = LibraryUiState(),
            currentTab = 0,
            changeTab = {},
            getReading = {}
        )
    }
}