package com.example.mangacat.ui.screens.home.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.example.mangacat.R
import com.example.mangacat.domain.model.HomeSeasonalMangaItem

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun SeasonalPanel(
    mangaList: List<HomeSeasonalMangaItem>,
    modifier: Modifier = Modifier,
    navigateToManga: (String) -> Unit
) {
    val itemsCount = mangaList.size

//    val startingPage = 0
    val numPages = Int.MAX_VALUE / itemsCount
    // todo fix this             here\/  (it shouldnt be -2)
    val startPage = (numPages / 2) - 2
//    val startIndex = (startPage * itemsCount) + startPage

    val pagerState = rememberPagerState(
        initialPage = startPage,

    ) {
        Int.MAX_VALUE
    }

    Box(
        modifier = modifier
    ) {
        HorizontalPager(
            state = pagerState,
            pageContent = {
                val index = it % itemsCount
                SeasonalItem(
                    cover = mangaList[index].cover,
                    publicationDemographic = mangaList[index].tags[0],
                    genres = listOf(mangaList[index].tags[1], mangaList[index].tags[2]),
                    mangaId = mangaList[index].id,
                    navigateToManga = navigateToManga
                )
            }
        )
        PageIndicator(
            mangaList = mangaList,
            pagerState = pagerState,
            modifier = Modifier
                .height(50.dp)
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(top = 30.dp)
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun PageIndicator(
    mangaList: List<HomeSeasonalMangaItem>,
    pagerState: PagerState,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.testTag("SeasonalPanelPager"),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(mangaList.size) { iteration ->
            val currentPage = pagerState.currentPage % mangaList.size

            val (color, size) = if (currentPage == iteration)
                MaterialTheme.colorScheme.primaryContainer to 12
            else
                MaterialTheme.colorScheme.outline to 8

            Box(
                modifier = Modifier
                    .padding(2.dp)
                    .clip(CircleShape)
                    .background(color)
                    .size(size.dp)
                    .border(
                        border = BorderStroke(0.2.dp, MaterialTheme.colorScheme.background),
                        shape = CircleShape
                    )
            )
        }
    }
}

@Composable
private fun SeasonalItem(
    cover: String,
    publicationDemographic: String?,
    genres: List<String?>,
    mangaId: String,
    modifier: Modifier = Modifier,
    navigateToManga: (String) -> Unit
) {
    Box(
        modifier = modifier.clickable {
            navigateToManga(mangaId)
        }
    ) {

        MangaCover(cover = cover, mangaId = mangaId)

        MangaGenreRow(
            publicationDemographic = publicationDemographic,
            genres = genres,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 25.dp)
        )
    }
}

@Composable
fun MangaCover(
    mangaId: String,
    cover: String,
    modifier: Modifier = Modifier
) {
    //toDo uncomment this later
//    Box {
//        SubcomposeAsyncImage(
//            model = ImageRequest
//                .Builder(LocalContext.current)
//                .data("https://uploads.mangadex.org/covers/$mangaId/$cover")
//                .crossfade(true)
//                .build(),
//            loading = { CircularProgressIndicator() },
//            contentDescription = null,
//            contentScale = ContentScale.Crop,
//            alpha = 0.7F,
//            modifier = modifier
//                .fillMaxWidth()
//                .height(dimensionResource(id = R.dimen.cover_size))
//                .blur(radius = 10.dp)
//                .clip(RectangleShape)
////                .background(
////                    Color.Black
////                )
//        )
//        SubcomposeAsyncImage(
//            model = ImageRequest
//                .Builder(LocalContext.current)
//                .data("https://uploads.mangadex.org/covers/$mangaId/$cover.256.jpg")
//                .crossfade(true)
//                .build(),
//            loading = { CircularProgressIndicator() },
//            contentDescription = null,
//            contentScale = ContentScale.FillHeight,
//            alpha = 0.7F,
//            modifier = modifier
//                .fillMaxWidth()
//                .height(dimensionResource(id = R.dimen.cover_size)),
//            colorFilter = ColorFilter.tint(
//                color = MaterialTheme.colorScheme.background.copy(alpha = 0.2F),
//                blendMode = BlendMode.Darken
//            )
//        )
//    }
}


@Composable
private fun MangaGenreRow(
    publicationDemographic: String?,
    genres: List<String?>,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_small)),
        modifier = modifier
    ) {
        GenreBadge(
            name = publicationDemographic!!,
            modifier = Modifier.background(color = MaterialTheme.colorScheme.primaryContainer)
        )
        GenreBadge(name = genres[0]!!)
        GenreBadge(name = genres[1]!!)
    }
}

@Composable
private fun GenreBadge(
    name: String,
    modifier: Modifier = Modifier
) {
    Card {
        Text(
            text = name, modifier = modifier
                .padding(dimensionResource(id = R.dimen.padding_small))
                .align(Alignment.CenterHorizontally),
            style = MaterialTheme.typography.labelSmall
        )
    }
}

//@Preview
//@Composable
//private fun SeasonalPanelPreview() {
//    MangaCatTheme {
//        SeasonalPanel(mangaList) {}
//    }
//}