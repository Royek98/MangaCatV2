package com.example.mangacat.ui.screens.home.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.example.mangacat.R
import com.example.mangacat.data.network.Resource
import com.example.mangacat.domain.model.HomeMangaItem
import com.example.mangacat.domain.model.HomeSeasonalMangaItem
import com.example.mangacat.ui.screens.home.HomeElements
import com.example.mangacat.ui.screens.home.LoadingScreen

@Composable
fun HorizontalPanel(
    title: String,
    mangaList: Resource<List<HomeMangaItem>>,
    modifier: Modifier = Modifier,
    testTag: String = "",
    navigateTo: () -> Unit
//    content: @Composable (HomeSeasonalMangaItem) -> Unit
) {
    Column(
        modifier = modifier
            .padding(dimensionResource(id = R.dimen.padding_small))
    ) {
        TitleBar(title = title, navigateTo = navigateTo)

        when (mangaList) {
            is Resource.Loading -> LoadingScreen()
            is Resource.Success -> {
                LazyRow(
                    modifier = Modifier
                        .height(200.dp)
                        .testTag(testTag),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(mangaList.data) { manga ->
                        Column(
                            horizontalAlignment = Alignment.Start,
                            modifier = Modifier
                                .width(135.dp)
                                .clickable { /* toDo */ }
                        ) {
                            Text(
                                text = manga.title,
                                overflow = TextOverflow.Ellipsis,
                                maxLines = 1
                            )
                            //toDo uncomment this later
                            SubcomposeAsyncImage(
                                model = ImageRequest
                                    .Builder(LocalContext.current)
                                    .data("https://uploads.mangadex.org/covers/${manga.id}/${manga.cover}")
                                    .crossfade(true)
                                    .build(),
                                loading = { CircularProgressIndicator() },
                                contentDescription = null,
                            )
                        }
                    }
                }
            }

            is Resource.Error -> {
                Text(text = "Error ${mangaList.message}")
            }
        }
    }
}

@Composable
private fun TitleBar(title: String, navigateTo: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.clickable { navigateTo() }
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.weight(1f)
        )
        Icon(
            imageVector = Icons.Default.KeyboardArrowRight,
            contentDescription = "Arrow right",
            modifier = Modifier
                .size(25.dp)
                .align(Alignment.CenterVertically)
        )
    }
}

//@Preview
//@Composable
//private fun HorizontalPanelPreview() {
//    MangaCatTheme {
//        HorizontalPanel("My series updates", mangaList = mangaList) {
//            Image(painter = painterResource(id = it.cover), contentDescription = null)
//            Text(text = it.title)
//        }
//    }
//}