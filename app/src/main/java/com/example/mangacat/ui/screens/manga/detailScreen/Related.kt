package com.example.mangacat.ui.screens.manga.detailScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.mangacat.ui.screens.manga.detailScreen.utils.OutlinedCard
import com.example.mangacat.ui.screens.manga.detailScreen.utils.TitleFlowRow

@Composable
fun Related(
    relatedUploaderList: List<String>,
    relatedScanlationGroupList: List<String>,
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

        //toDo display related manga (cover, title, type eg. doujin)
    }
}

@Preview(showBackground = true)
@Composable
fun RelatedPreview() {
    Related(
        relatedUploaderList = listOf("user1", "user2"),
        relatedScanlationGroupList = listOf("group1", "group2")
    )
}