package com.example.mangacat.ui.screens.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import com.example.mangacat.R
import com.example.mangacat.domain.model.HomeSeasonalMangaItem

@Composable
fun HorizontalPanel(
    title: String,
    mangaList: List<HomeSeasonalMangaItem>,
    modifier: Modifier = Modifier,
    testTag: String = "",
    content: @Composable (HomeSeasonalMangaItem) -> Unit
) {
    Column(
        modifier = modifier
            .padding(dimensionResource(id = R.dimen.padding_small))
    ) {
        TitleBar(title = title)

        LazyRow(
            modifier = Modifier
                .height(200.dp)
                .testTag(testTag),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(mangaList) { manga ->
                Column(
                    horizontalAlignment = Alignment.Start
                ) {
                    content(manga)
                }
            }
        }
    }
}

@Composable
private fun TitleBar(title: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge
            )
        }
        Column(
            modifier = Modifier.align(Alignment.CenterVertically)
        ) {
            Icon(
                imageVector = Icons.Default.KeyboardArrowRight,
                contentDescription = "Arrow right",
                modifier = Modifier.size(25.dp)
            )
        }
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