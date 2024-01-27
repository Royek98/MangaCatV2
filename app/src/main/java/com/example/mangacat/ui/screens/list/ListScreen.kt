package com.example.mangacat.ui.screens.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.RadioButtonChecked
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mangacat.R
import com.example.mangacat.data.dto.manga.enums.ContentRating
import com.example.mangacat.data.dto.manga.enums.Status
import com.example.mangacat.data.network.Resource
import com.example.mangacat.domain.model.MangaListItem
import com.example.mangacat.ui.screens.utils.MangaCard
import com.example.mangacat.ui.screens.utils.TemplateScaffold
import com.example.mangacat.ui.theme.MangaCatTheme

@Composable
fun ListScreen(
    viewModel: ListViewModel,
    title: String,
    navigateBack: () -> Unit
) {

    ListContent(
        title = title,
        navigateBack = navigateBack,
        uiState = viewModel.uiState.collectAsState().value
    )
}

@Composable
private fun ListContent(
    title: String,
    uiState: Resource<List<MangaListItem>>,
    navigateBack: () -> Unit
) {
    when (uiState) {
        is Resource.Loading -> {}
        is Resource.Success -> {
            Success(
                title = title,
                mangaList = uiState.data,
                navigateBack = navigateBack
            )
        }

        is Resource.Error -> {}
    }
}
@Composable
private fun Success(
    title: String,
    mangaList: List<MangaListItem>,
    navigateBack: () -> Unit
) {
    TemplateScaffold(title = title, navigateBack = { navigateBack() }) { paddingValues ->
        LazyColumn(
            modifier = Modifier.padding(top = paddingValues.calculateTopPadding()),
            contentPadding = PaddingValues(5.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(mangaList) { manga ->
                MangaCard(title = manga.title, mangaId = manga.id, coverId = manga.cover) {
                    StatusItem(
                        icon = Icons.Default.RadioButtonChecked,
                        iconDescription = "Status ${manga.status.name}",
                        text = manga.status.name,
                        tint = when (manga.status) {
                            Status.COMPLETED -> Color.Blue
                            Status.ONGOING -> Color.Green
                            Status.HIATUS -> Color.Yellow
                            Status.CANCELLED -> Color.Red
                        }
                    )

                    TagsRow(
                        contentRating = manga.contentRating,
                        content = manga.content,
                        format = manga.format,
                        genres = manga.genres,
                        themes = manga.themes
                    )

                    Text(
                        text = manga.description,
                        style = MaterialTheme.typography.bodySmall,
                        maxLines = 7,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
            }
        }
    }
}

@Composable
private fun StatusItem(
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
            modifier = Modifier
                .size(10.dp)
                .align(Alignment.CenterVertically)
        )
        Text(
            text = text,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(start = 1.dp)
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun TagsRow(
    contentRating: ContentRating,
    content: List<String?>,
    format: List<String?>,
    genres: List<String?>,
    themes: List<String?>,

    ) {
    val paddingSmall = dimensionResource(id = R.dimen.padding_small)
    FlowRow(
        modifier = Modifier.padding(paddingSmall),
        horizontalArrangement = Arrangement.spacedBy(paddingSmall),
        content = {
            if (contentRating != ContentRating.SAFE) {
                Text(
                    text = contentRating.name,
                    color = MaterialTheme.colorScheme.onPrimary,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier
                        .background(
                            color = MaterialTheme.colorScheme.primary,
                            shape = RoundedCornerShape(5.dp)
                        )
                        .padding(2.dp)
                )
            }
            content.forEach { content ->
                content?.let {
                    Text(
                        text = content,
                        color = MaterialTheme.colorScheme.onError,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier
                            .background(
                                color = MaterialTheme.colorScheme.error,
                                shape = RoundedCornerShape(5.dp)
                            )
                            .padding(2.dp)
                    )
                }
            }
            format.forEach { format ->
                format?.let {
                    Text(
                        text = format,
                        color = MaterialTheme.colorScheme.onSecondary,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier
                            .background(
                                color = MaterialTheme.colorScheme.secondary,
                                shape = RoundedCornerShape(5.dp)
                            )
                            .padding(2.dp)
                    )
                }
            }
            genres.forEach { genre ->
                genre?.let {
                    Text(text = genre, style = MaterialTheme.typography.bodySmall)
                }
            }
//            themes?.forEach { theme ->
//                theme?.let {
//                    Text(text = theme, style = MaterialTheme.typography.bodySmall)
//                }
//            }
        },
        verticalArrangement = Arrangement.spacedBy(paddingSmall)
    )
}

@Preview
@Composable
fun ListScreenPreview() {
    MangaCatTheme {
        Success(
            title = "Stuff Picks",
            mangaList = listOf(
                MangaListItem(
                    id = "Id",
                    title = "Title1",
                    status = Status.COMPLETED,
                    contentRating = ContentRating.SAFE,
                    description = "description",
                    cover = "",
                    genres = listOf("genre1", "genre2"),
                    themes = listOf("theme1", "theme2"),
                    format = listOf("format1", "format2"),
                    content = listOf("content1", "content2")
                ),
                MangaListItem(
                    id = "Id",
                    title = "Title2",
                    status = Status.ONGOING,
                    contentRating = ContentRating.EROTICA,
                    description = "description",
                    cover = "",
                    genres = listOf("genre1", "genre2"),
                    themes = listOf("theme1", "theme2"),
                    format = listOf("format1", "format2"),
                    content = listOf("content1", "content2")
                ),
            )
        ) {

        }
    }
}
