package com.example.mangacat.ui.screens.manga

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
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
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    when (mangaUiState) {
        is Resource.Loading -> LoadingScreen()
        is Resource.Success -> Success(manga = mangaUiState.data, navigateBack)
        is Resource.Error -> ErrorScreen({})
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun Success(
    manga: Manga,
    navigateBack: () -> Unit,
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
            PagerContent(pageIndex, manga, modifierSurfaceVariant)
        }
    }
}

@Composable
private fun PagerContent(
    pageIndex: Int,
    manga: Manga,
    modifier: Modifier = Modifier
) {
    val paddingSmall = dimensionResource(id = R.dimen.padding_small)
    val modifierPadding = modifier
        .padding(start = paddingSmall, end = paddingSmall)
    when (pageIndex) {
        0 -> Details(manga = manga, modifier = modifierPadding)
        1 -> Related()
        2 -> CoverList()
    }

}

@Composable
private fun Related(
    modifier: Modifier = Modifier
) {
    Text(text = "Related")
}

@Composable
private fun CoverList(
    modifier: Modifier = Modifier
) {
    Text(text = "List of covers")
}

@Composable
private fun Details(
    manga: Manga,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.verticalScroll(rememberScrollState())
    ) {
        Text(text = manga.description.en!!)

        Spacer(modifier = Modifier.size(dimensionResource(id = R.dimen.padding_small)))

        PairColumn(
            leftColumn = {
                TitleFlowRow(title = "Publication year") {
                    OutlinedCard(text = manga.year.toString())
                }
            },
            rightColumn = {
                TitleFlowRow(title = "Status") {
                    OutlinedCard(text = manga.status.name)
                }
            }
        )

        PairColumn(
            leftColumn = {
                TitleFlowRow(title = "Author") {
                    OutlinedCard(text = manga.author)
                }
            },
            rightColumn = {
                TitleFlowRow(title = "Artist") {
                    OutlinedCard(text = manga.artist)
                }
            }
        )

        PairColumn(
            leftColumn = {
                if (manga.publicationDemographic?.name != null) {
                    TitleFlowRow(title = "Demographic") {
                        OutlinedCard(text = manga.publicationDemographic.name)
                    }
                }
            },
            rightColumn = {
                if (manga.format.isNotEmpty()) {
                    TitleFlowRow(title = "Format") {
                        repeat(manga.format.size) {
                            OutlinedCard(text = manga.format[it] ?: "")
                        }
                    }
                }
            }
        )

        PairColumn(
            leftColumn = {
                if (manga.themes.isNotEmpty()) {
                    TitleFlowRow(title = "Themes") {
                        repeat(manga.themes.size) {
                            OutlinedCard(text = manga.themes[it] ?: "")
                        }
                    }
                }
            },
            rightColumn = {
                TitleFlowRow(title = "Content Rating") {
                    OutlinedCard(text = manga.contentRating.name)
                }
            }
        )

        if (manga.genres.isNotEmpty()) {
            TitleFlowRow(title = "Genres") {
                repeat(manga.genres.size) {
                    OutlinedCard(text = manga.genres[it] ?: "")
                }
            }
        }

    }
}

@Composable
private fun PairColumn(
    leftColumn: @Composable () -> Unit,
    rightColumn: @Composable () -> Unit
) {
    Row {
        Column(modifier = Modifier.weight(0.5f)) {
            leftColumn()
        }
        Column(modifier = Modifier.weight(0.5f)) {
            rightColumn()
        }
    }
}

@Composable
@OptIn(ExperimentalLayoutApi::class)
private fun TitleFlowRow(
    title: String,
    flowRowScope: @Composable () -> Unit
) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleLarge
    )

    val paddingSmall = dimensionResource(id = R.dimen.padding_small)
    FlowRow(
        modifier = Modifier.padding(paddingSmall),
        horizontalArrangement = Arrangement.spacedBy(paddingSmall),
        content = { flowRowScope() },
        verticalArrangement = Arrangement.spacedBy(paddingSmall)
    )
}

@Composable
private fun OutlinedCard(
    text: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
//                elevation = CardDefaults.cardElevation(5.dp),
        border = BorderStroke(1.dp, Color.Black)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_small))
        )
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
            jumpToIndex = jumpToIndex,
            index = 0,
            currentPage = currentPage
        )

        PagerNavigationButton(
            navigationTitle = "Related",
            jumpToIndex = jumpToIndex,
            index = 1,
            currentPage = currentPage
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
    )

    Details(manga = manga)
}
