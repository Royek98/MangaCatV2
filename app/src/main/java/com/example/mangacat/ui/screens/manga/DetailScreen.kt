package com.example.mangacat.ui.screens.manga

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mangacat.R
import com.example.mangacat.data.dto.English
import com.example.mangacat.data.dto.manga.enums.ContentRating
import com.example.mangacat.data.dto.manga.enums.PublicationDemographic
import com.example.mangacat.data.dto.manga.enums.Status
import com.example.mangacat.data.network.Resource
import com.example.mangacat.domain.model.Manga
import com.example.mangacat.ui.screens.home.ErrorScreen
import com.example.mangacat.ui.screens.home.LoadingScreen
import kotlinx.coroutines.launch

@Composable
fun DetailScreen(
    mangaUiState: Resource<Manga>,
    navigateBack: () -> Unit
) {
    when (mangaUiState) {
        is Resource.Loading -> LoadingScreen()
        is Resource.Success -> Success(manga = mangaUiState.data, navigateBack)
        is Resource.Error -> ErrorScreen({})
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
private fun Success(
    manga: Manga,
    navigateBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .fillMaxSize()
    ) {

        TopBar(title = manga.title.en!!, navigateBack = navigateBack)

        val pagerState = rememberPagerState(pageCount = {
            3
        })
        val coroutineScope = rememberCoroutineScope()

        PageIndicator(pagerState.currentPage) { index ->
            coroutineScope.launch {
                pagerState.scrollToPage(index)
            }
        }

        Spacer(modifier = Modifier.size(dimensionResource(id = R.dimen.padding_medium)))

        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.Top
        ) { pageIndex ->
            PagerContent(pageIndex, manga)
        }
    }
}

@Composable
private fun PagerContent(pageIndex: Int, manga: Manga) {
    when (pageIndex) {
        0 -> Details(manga = manga)
        1 -> CoverList()
        2 -> Related()
    }
}

@Composable
private fun Related() {
    Text(text = "Related")
}

@Composable
private fun CoverList() {
    Text(text = "List of covers")
}

@Composable
private fun Details(manga: Manga) {
    Column {
        Text(text = manga.description.en!!)
        Text(text = "Author")
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopBar(
    title: String,
    navigateBack: () -> Unit
) {
    CenterAlignedTopAppBar(
        title = { Text(text = title) },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent),
        actions = {
            IconButton(
                onClick = navigateBack,
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

@Composable
private fun PageIndicator(
    currentPage: Int,
    jumpToIndex: (Int) -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier.fillMaxWidth()
    ) {
        PagerNavigationButton(
            navigationTitle = "Details",
            jumpToIndex = jumpToIndex,
            index = 0,
            currentPage = currentPage
        )

        PagerNavigationButton(
            navigationTitle = "Covers",
            jumpToIndex = jumpToIndex,
            index = 1,
            currentPage = currentPage
        )

        PagerNavigationButton(
            navigationTitle = "Related",
            index = 2,
            jumpToIndex = jumpToIndex,
            currentPage = currentPage
        )
    }
}

@Composable
private fun PagerNavigationButton(
    navigationTitle: String,
    index: Int,
    currentPage: Int,
    jumpToIndex: (Int) -> Unit,
){
    var color = MaterialTheme.colorScheme.secondary

    if (currentPage == index) color = MaterialTheme.colorScheme.primary

    Button(
        onClick = { jumpToIndex(index) },
        shape = RectangleShape,
        modifier = Modifier.drawBehind {
            drawLine(
                color = color,
                start = Offset(0f, size.height),
                end = Offset(size.width, size.height),
                strokeWidth = 9f
            )
        },
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            contentColor = color
        )
    ) {
        Text(text = navigationTitle) // Details
    }
}

@Composable
@Preview(showBackground = true)
fun SuccessPreview() {
    val manga = Manga(
        id = "id",
        title = English("title"),
        description = English(
            "very long description about the title you are currently reading. " +
                    "hope you have a great time and enjoy this piece of culture"
        ),
        publicationDemographic = PublicationDemographic.SEINEN,
        contentRating = ContentRating.SAFE,
        status = Status.COMPLETED,
        year = 2023,
        author = "author name and surname",
        cover = "cover"
    )
    Success(manga) {}
}