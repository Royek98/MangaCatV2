package com.example.mangacat.ui.screens.utils

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.PeopleAlt
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.filled.WatchLater
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mangacat.domain.model.ChapterFeedItem
import com.example.mangacat.domain.model.MangaFeedItem
import com.example.mangacat.ui.theme.MangaCatTheme

@Composable
fun ChaptersUpdateCard(manga: MangaFeedItem) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 5.dp
        )
    ) {
        Column(
            modifier = Modifier.padding(10.dp)
        ) {
            Text(text = manga.titleManga)
            Divider(
                modifier = Modifier.padding(5.dp),
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
            Row {
                Image(
                    imageVector = manga.cover,
                    contentDescription = "",
                    modifier = Modifier.weight(0.2f),
                    contentScale = ContentScale.Crop
                )
                Column(
                    modifier = Modifier.weight(0.8f)
                ) {
                    repeat(manga.chapterList.size) {
                        ChapterItem(chapter = manga.chapterList[it])
                        Divider(
                            modifier = Modifier.padding(5.dp),
                            color = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ChapterItem(chapter: ChapterFeedItem) {
    Row {
        if (chapter.hasSeen) {
            Icon(imageVector = Icons.Default.VisibilityOff, contentDescription = "")
        } else {
            Icon(imageVector = Icons.Default.Visibility, contentDescription = "")
        }
        Text(text = chapter.chapterTitle)
    }
    Row {
        Icon(imageVector = Icons.Default.PeopleAlt, contentDescription = "")
        Text(text = chapter.userNameUploader)
    }
    Row {
        Row(
            modifier = Modifier.weight(0.6f),
            horizontalArrangement = Arrangement.Start
        ) {
            Icon(imageVector = Icons.Default.Person, contentDescription = "")
            Text(text = chapter.groupNameUploader)
        }
        Row(
            modifier = Modifier.weight(0.4f),
            horizontalArrangement = Arrangement.End
        ) {
            Icon(imageVector = Icons.Default.WatchLater, contentDescription = "")
            Text(text = chapter.timestamp)
        }
    }
}

@Composable
@Preview
fun ChaptersUpdateCardPreview() {
    MangaCatTheme {
        ChaptersUpdateCard(
            MangaFeedItem(
                mangaId = "",
                cover = Icons.Default.Image,
                titleManga = "Title",
                chapterList = listOf(
                    ChapterFeedItem(
                        chapterId = "",
                        chapterTitle = "Chapter Title",
                        userNameUploader = "Username",
                        groupNameUploader = "Groupname",
                        timestamp = "2023-11-23",
                        hasSeen = false
                    ),
                    ChapterFeedItem(
                        chapterId = "",
                        chapterTitle = "Chapter Title2",
                        userNameUploader = "Username2",
                        groupNameUploader = "Groupname2",
                        timestamp = "2023-11-24",
                        hasSeen = true
                    ),
                )
            )
        )
    }
}