package com.example.mangacat.domain.model

data class HomeLatestUpdate(
    val mangaId: String,
    val chapterId: String,
    val title: String,
    var cover: String? = null,
    val chapterTitle: String,
    val groupName: String? = null,
    val userName: String? = null
)
