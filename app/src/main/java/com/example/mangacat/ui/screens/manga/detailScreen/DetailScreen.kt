package com.example.mangacat.ui.screens.manga.detailScreen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import com.example.mangacat.domain.model.Cover
import com.example.mangacat.domain.model.Manga
import com.example.mangacat.ui.screens.home.ErrorScreen
import com.example.mangacat.ui.screens.home.LoadingScreen
import com.example.mangacat.ui.screens.manga.MangaViewModel
import kotlinx.coroutines.launch

@Composable
fun DetailScreen(
    viewModel: MangaViewModel,
    navigateBack: () -> Unit,
) {
    DetailContent(
        mangaUiState = viewModel.mangaUiState,
        coverList = viewModel.coverList,
        relatedMangaListCover = viewModel.relatedMangaListCover,
        relatedUploaderList = viewModel.uploaderList,
        relatedScanlationGroupList = viewModel.scanlationGroupList,
        navigateBack = navigateBack,
        getCoverList = viewModel::getCoverList,
        getRelatedMangaListCover = viewModel::getRelatedMangaListCover
    )
}

@Composable
private fun DetailContent(
    mangaUiState: Resource<Manga>,
    coverList: Resource<List<Cover>>,
    relatedMangaListCover: Resource<List<Pair<String, String>>>,
    relatedUploaderList: List<String>,
    relatedScanlationGroupList: List<String>,
    navigateBack: () -> Unit,
    getCoverList: () -> Unit,
    getRelatedMangaListCover: () -> Unit,
    modifier: Modifier = Modifier
) {
    when (mangaUiState) {
        is Resource.Loading -> LoadingScreen()
        is Resource.Success -> Success(
            manga = mangaUiState.data,
            navigateBack = navigateBack,
            relatedUploaderList = relatedUploaderList,
            relatedScanlationGroupList = relatedScanlationGroupList,
            getCoverList = getCoverList,
            coverList = coverList,
            getRelatedMangaListCover = getRelatedMangaListCover,
            relatedMangaListCover = relatedMangaListCover
        )

        is Resource.Error -> ErrorScreen({})
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun Success(
    manga: Manga,
    relatedUploaderList: List<String>,
    relatedScanlationGroupList: List<String>,
    navigateBack: () -> Unit,
    getCoverList: () -> Unit,
    coverList: Resource<List<Cover>>,
    getRelatedMangaListCover: () -> Unit,
    relatedMangaListCover: Resource<List<Pair<String, String>>>,
    modifier: Modifier = Modifier
) {
    val modifierSurfaceVariant = Modifier
        .background(MaterialTheme.colorScheme.surfaceVariant)
    Column(
        modifier = modifierSurfaceVariant
            .fillMaxSize()
    ) {

        TopBar(title = manga.title.en!!, navigateBack = navigateBack)

        val pagerState = rememberPagerState(pageCount = {
            3
        })
        val coroutineScope = rememberCoroutineScope()

        PageIndicator(
            currentPage = pagerState.currentPage
        ) { index ->
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
            if (pageIndex == 2) {
                getCoverList()
            } else if (pageIndex == 1) {
                getRelatedMangaListCover()
            }

            PagerContent(
                pageIndex = pageIndex,
                manga = manga,
                relatedUploaderList = relatedUploaderList,
                relatedScanlationGroupList = relatedScanlationGroupList,
                coverList = coverList,
                relatedMangaListCover = relatedMangaListCover,
                modifier = modifierSurfaceVariant
            )
        }
    }
}

@Composable
private fun PagerContent(
    pageIndex: Int,
    manga: Manga,
    relatedUploaderList: List<String>,
    relatedScanlationGroupList: List<String>,
    coverList: Resource<List<Cover>>,
    relatedMangaListCover: Resource<List<Pair<String, String>>>,
    modifier: Modifier = Modifier
) {
    val paddingSmall = dimensionResource(id = R.dimen.padding_small)
    val modifierPadding = modifier
        .padding(start = paddingSmall, end = paddingSmall)
    when (pageIndex) {
        0 -> Details(manga = manga, modifier = modifierPadding)
        1 -> Related(
            relatedUploaderList = relatedUploaderList,
            relatedScanlationGroupList = relatedScanlationGroupList,
            relatedManga = manga.related,
            relatedMangaListCover = relatedMangaListCover,
            modifier = modifierPadding
        )
        2 -> Covers(coverList = coverList, mangaId = manga.id, modifier = modifierPadding)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopBar(
    title: String,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier
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
    modifier: Modifier = Modifier,
    jumpToIndex: (Int) -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier.fillMaxWidth()
    ) {
        PagerNavigationButton(
            navigationTitle = "Details",
            index = 0,
            currentPage = currentPage,
            jumpToIndex = jumpToIndex
        )

        PagerNavigationButton(
            navigationTitle = "Related",
            index = 1,
            currentPage = currentPage,
            jumpToIndex = jumpToIndex
        )

        PagerNavigationButton(
            navigationTitle = "Covers",
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
    modifier: Modifier = Modifier
) {
    var color = MaterialTheme.colorScheme.secondary

    if (currentPage == index) color = MaterialTheme.colorScheme.primary

    Button(
        onClick = {
            jumpToIndex(index)
        },
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

//@Composable
//@Preview(showBackground = true)
//fun SuccessPreview() {
//    val manga = Manga(
//        id = "id",
//        title = English("title"),
//        description = English(
//            "very long description about the title you are currently reading. " +
//                    "hope you have a great time and enjoy this piece of culture"
//        ),
//        publicationDemographic = PublicationDemographic.SEINEN,
//        contentRating = ContentRating.SAFE,
//        status = Status.COMPLETED,
//        year = 2023,
//        author = "author name and surname",
//        cover = "cover",
//        artist = "artist name and surname"
//    )
//    Success(manga = manga, navigateBack = {})
//}

@Composable
@Preview(showBackground = true)
fun DetailsPreview() {
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
        cover = "cover",
        artist = "artist name and surname",
        genres = listOf("Comedy", "Romance", "Slice of Life"),
        themes = listOf("School life"),
        format = listOf("Adaptation"),
        related = listOf()
    )

    Details(manga = manga)
}
