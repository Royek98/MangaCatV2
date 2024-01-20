package com.example.mangacat.domain.model

import androidx.compose.ui.graphics.vector.ImageVector

data class MangaFeedItem(
    val mangaId: String,
    val cover: ImageVector,
    val titleManga: String,
    val chapterList: List<ChapterFeedItem>
)

data class ChapterFeedItem(
    val chapterId: String,
    val chapterTitle: String,
    val userNameUploader: String,
    val groupNameUploader: String,
    val timestamp: String,
    val hasSeen: Boolean
)
