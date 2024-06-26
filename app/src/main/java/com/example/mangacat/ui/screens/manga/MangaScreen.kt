package com.example.mangacat.ui.screens.manga

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.RadioButtonChecked
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.outlined.AccessTime
import androidx.compose.material.icons.outlined.PeopleAlt
import androidx.compose.material.icons.outlined.PersonOutline
import androidx.compose.material.icons.outlined.StarBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.mangacat.R
import com.example.mangacat.data.dto.manga.enums.Status
import com.example.mangacat.data.network.Resource
import com.example.mangacat.domain.model.Chapter
import com.example.mangacat.domain.model.Manga
import com.example.mangacat.ui.screens.home.LoadingScreen
import com.example.mangacat.ui.screens.home.components.MangaCover
import com.example.mangacat.ui.screens.manga.utils.formatNumber
import com.example.mangacat.ui.screens.utils.ErrorMessage
import com.example.mangacat.ui.screens.utils.TopAppBarBackAndAction

@Composable
fun MangaScreen(
    mangaId: String,
    viewModel: MangaViewModel,
    navigateBack: () -> Unit,
    navigateToRead: (String, String) -> Unit,
    navigateToDetail: () -> Unit
) {
    MangaContent(
        mangaUiState = viewModel.mangaUiState,
        chapterListUiState = viewModel.chapterListUiState,
        retryManga = { viewModel.getManga(mangaId) },
        retryChapterList = { viewModel.getChapterList(mangaId) },
        navigateBack = navigateBack,
        navigateToRead = navigateToRead,
        navigateToDetail = navigateToDetail,
        mangaId = mangaId
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MangaContent(
    mangaId: String,
    mangaUiState: Resource<Manga>,
    chapterListUiState: Resource<List<Chapter>>,
    retryManga: () -> Unit,
    retryChapterList: () -> Unit,
    navigateBack: () -> Unit,
    navigateToRead: (String, String) -> Unit,
    navigateToDetail: () -> Unit
) {
    Column {
        TopAppBarBackAndAction(
            actionImageVector = Icons.Default.Info,
            actionContentDescription = "Manga details",
            action = navigateToDetail,
            navigateBack = navigateBack
        )
        when (mangaUiState) {
            is Resource.Loading -> LoadingScreen()
            is Resource.Success -> TopBarSuccess(mangaUiState.data, navigateBack, navigateToDetail)
            is Resource.Error -> ErrorMessage(
                retryAction = retryManga,
                errorMessages = mangaUiState.message,
            )
        }

        when (chapterListUiState) {
            is Resource.Loading -> LoadingScreen()
            is Resource.Success -> ChapterListSuccess(chapterListUiState.data, navigateToRead)
            is Resource.Error -> ErrorMessage(
                retryAction = retryChapterList,
                errorMessages = chapterListUiState.message,
            )
        }
    }
}

@Composable
private fun ChapterListSuccess(
    chapterList: List<Chapter>,
    navigateToRead: (String, String) -> Unit
) {
    LazyColumn {
        items(chapterList) { chapter ->
            ChapterItem(
                visibility = Icons.Default.Visibility,
                chapter = chapter,
                bgColor = MaterialTheme.colorScheme.primaryContainer,
                navigateToRead = navigateToRead
            )
        }
    }
}

@Composable
fun TopBarSuccess(
    manga: Manga,
    navigateBack: () -> Unit,
    navigateToDetail: () -> Unit
) {
    Box {
        Details(manga = manga)
    }
}

@Composable
private fun ChapterItem(
    visibility: ImageVector,
    chapter: Chapter,
    bgColor: Color,
    modifier: Modifier = Modifier,
    navigateToRead: (String, String) -> Unit
) {
    val contentColor = if (isSystemInDarkTheme()) Color.White else Color.Black
    Button(
        onClick = {
            navigateToRead(chapter.chapterId, chapter.mangaId)
        },
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 1.dp),
        colors = ButtonDefaults
            .buttonColors(
                containerColor = bgColor,
                contentColor = contentColor
            ),
        shape = RectangleShape,
    ) {
        Row {

            Column(
                modifier = Modifier
            ) {
                ChapterItemRow(
                    imageVector = visibility,
                    contentDescription = "Chapter reading status",
                    text = "Ch. ${chapter.chapterNumber}",
                    style = MaterialTheme.typography.bodyMedium
                )

                ChapterItemRow(
                    imageVector = Icons.Outlined.PeopleAlt,
                    contentDescription = "Group name",
                    text = chapter.scanlationGroupName,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Spacer(modifier = Modifier.weight(0.1F))

            Column {
                ChapterItemRow(
                    imageVector = Icons.Outlined.AccessTime,
                    contentDescription = "Upload date",
                    text = chapter.updatedAt,
                    style = MaterialTheme.typography.bodyMedium
                )

                ChapterItemRow(
                    imageVector = Icons.Outlined.PersonOutline,
                    contentDescription = "Uploader",
                    text = chapter.uploaderUsername,
                    style = MaterialTheme.typography.bodyMedium
                )
            }


            Spacer(modifier = Modifier.weight(0.9F))
            Text(
                text = chapter.chapterNumber,
                modifier = Modifier.align(Alignment.CenterVertically)
            )
        }
    }

}

@Composable
private fun ChapterItemRow(
    imageVector: ImageVector,
    contentDescription: String,
    text: String,
    style: TextStyle,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Icon(
            imageVector = imageVector,
            contentDescription = contentDescription,
            modifier = Modifier.size(15.dp)
        )

        Text(
            text = text,
            style = style,
            modifier = Modifier.padding(start = 2.dp)
        )
    }
}


@Composable
private fun Details(
    manga: Manga,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.height(dimensionResource(id = R.dimen.cover_size)),
    ) {
        MangaCover(cover = manga.cover, mangaId = manga.id)

        DetailsBar(manga = manga)
    }
}

@Composable
private fun DetailsBar(
    manga: Manga,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .height(dimensionResource(id = R.dimen.cover_size)),
        verticalArrangement = Arrangement.Bottom
    ) {
        Column(
            modifier = Modifier
                .clip(RectangleShape)
                .background(MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.85F))
                .padding(horizontal = dimensionResource(id = R.dimen.padding_medium)),
        ) {
            TextInfo(
                text = manga.title.en ?: "",
                style = MaterialTheme.typography.titleLarge
            )

            TextInfo(
                text = manga.author,
                style = MaterialTheme.typography.titleMedium
            )

            TextInfo(
                text = manga.description.en ?: "",
                style = MaterialTheme.typography.bodySmall
            )

            Spacer(modifier = Modifier.height(15.dp))

            // todo
//            StatisticsRow(
//                rating = manga.rating,
//                follows = manga.follows,
//                year = manga.year,
//                status = manga.status
//            )
        }
    }
}

@Composable
private fun TextInfo(
    text: String,
    style: TextStyle,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        style = style,
        maxLines = 2,
        overflow = TextOverflow.Ellipsis,
        modifier = modifier
    )
}

@Composable
private fun StatisticsRow(
    rating: Float,
    follows: Int,
    year: Int,
    status: Status,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = dimensionResource(id = R.dimen.padding_medium))
    ) {
        val tint = if (isSystemInDarkTheme()) Color.Black else Color.White

        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = { /*TODO*/ },
                modifier = modifier.height(25.dp),
                contentPadding = PaddingValues(0.dp)
            ) {
                StatisticsItem(
                    icon = Icons.Outlined.StarBorder,
                    iconDescription = "Rating",
                    text = String.format("%.2f", rating).replace(",", "."),
                    tint = tint,
                )
            }

            Button(
                onClick = { /*TODO*/ },
                modifier = modifier.height(25.dp),
                contentPadding = PaddingValues(0.dp)
            ) {
                StatisticsItem(
                    icon = Icons.Default.FavoriteBorder,
                    iconDescription = "Bookmarked",
                    text = formatNumber(follows),
                    tint = tint
                )
            }

            StatisticsItem(
                icon = Icons.Default.RadioButtonChecked,
                iconDescription = "Information",
                text = "Publication: $year, $status",
                tint = when (status) {
                    Status.COMPLETED -> Color.Blue
                    Status.ONGOING -> Color.Green
                    Status.HIATUS -> Color.Yellow
                    Status.CANCELLED -> Color.Red
                }
            )
        }
    }
}

@Composable
private fun StatisticsItem(
    icon: ImageVector,
    iconDescription: String,
    text: String,
    modifier: Modifier = Modifier,
    tint: Color
) {
    Row(
        modifier = modifier
    ) {
        Icon(
            imageVector = icon,
            contentDescription = iconDescription,
            tint = tint,
            modifier = Modifier.size(20.dp)
        )
        Text(
            text = text,
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier.padding(start = 1.dp)
        )
    }
}