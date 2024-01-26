package com.example.mangacat.ui.screens.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.PeopleAlt
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.example.mangacat.domain.model.HomeLatestUpdate

@Composable
fun LatestUpdates(mangaList: List<HomeLatestUpdate>) {
    Column(
//        modifier = Modifier
//            .padding(dimensionResource(id = R.dimen.padding_small))
    ) {
        TitleBar(title = "Latest Updates", navigateTo = {})
        mangaList.forEach {
            NewChapterItem(it)
        }
    }
}

@Composable
private fun NewChapterItem(
    manga: HomeLatestUpdate
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(1.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Row {
//            Image(
//                imageVector = Icons.Default.Image,
//                contentDescription = "",
//                modifier = Modifier
//                    .weight(0.2f)
//                    .clickable { /* todo */ },
//                contentScale = ContentScale.Crop
//            )
            SubcomposeAsyncImage(
                model = ImageRequest
                    .Builder(LocalContext.current)
                    .data("https://uploads.mangadex.org/covers/${manga.mangaId}/${manga.cover}.256.jpg")
                    .crossfade(true)
                    .build(),
                loading = { CircularProgressIndicator() },
                contentDescription = null,
                modifier = Modifier
                    .weight(0.2f)
            )
            Column(
                modifier = Modifier
                    .weight(0.8f)
                    .padding(start = 5.dp)
                    .clickable { /* todo */ }
            ) {
                Text(text = manga.title)
                Text(text = manga.chapterTitle)
                Row {
                    if (manga.groupName != null) {
                        Icon(imageVector = Icons.Default.PeopleAlt, contentDescription = "")
                        Text(text = manga.groupName)
                    } else {
                        Icon(imageVector = Icons.Default.Person, contentDescription = "")
                        Text(text = manga.userName ?: "No info")
                    }
                }
            }
        }
    }
}