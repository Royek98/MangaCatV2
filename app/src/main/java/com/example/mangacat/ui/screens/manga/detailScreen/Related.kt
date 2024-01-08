package com.example.mangacat.ui.screens.manga.detailScreen

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.example.mangacat.data.dto.MangaIncludes
import com.example.mangacat.data.network.Resource
import com.example.mangacat.ui.screens.manga.detailScreen.utils.OutlinedCard
import com.example.mangacat.ui.screens.manga.detailScreen.utils.TitleFlowRow
import com.example.mangacat.utils.toTitle

@Composable
fun Related(
    relatedUploaderList: List<String>,
    relatedScanlationGroupList: List<String>,
    relatedManga: List<MangaIncludes?>,
    relatedMangaListCover: Resource<List<Pair<String, String>>>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        TitleFlowRow(title = "Users") {
            repeat(relatedUploaderList.size) {
                OutlinedCard(text = relatedUploaderList[it])
            }
        }
        TitleFlowRow(title = "Groups") {
            repeat(relatedScanlationGroupList.size) {
                OutlinedCard(text = relatedScanlationGroupList[it])
            }
        }

        // todo Finish this later, when stop using fake repository
        when(relatedMangaListCover) {
            is Resource.Loading -> {}
            is Resource.Success -> {
                val relatedByGroup = relatedManga.groupBy { it?.related }
                relatedByGroup.forEach{ related ->
                    Column {
                        Text(
                            text = related.key!!.toTitle(),
                            style = MaterialTheme.typography.titleLarge
                        )
                        LazyRow {
                            items(related.value) { manga ->
                                val test = relatedMangaListCover.data.find {pair-> pair.first == manga!!.id }?.second
                                SubcomposeAsyncImage(
                                    model = ImageRequest
                                        .Builder(LocalContext.current)
                                        .data("https://uploads.mangadex.org/covers/${manga!!.id}/${test}")
                                        .crossfade(true)
                                        .build(),
                                    contentDescription = "",
                                    loading = { CircularProgressIndicator() },
                                )
//                                Text(text = it!!.title.en!!)

                            }
                        }
                    }
                }
            }
            is Resource.Error -> {}
        }
        //toDo display related manga (cover, title, type eg. doujin)
    }
}

//@Preview(showBackground = true)
//@Composable
//fun RelatedPreview() {
//    Related(
//        relatedUploaderList = listOf("user1", "user2"),
//        relatedScanlationGroupList = listOf("group1", "group2"),
//        relatedManga = listOf(),
//        relatedMangaListCover = listOf<>()
//    )
//}