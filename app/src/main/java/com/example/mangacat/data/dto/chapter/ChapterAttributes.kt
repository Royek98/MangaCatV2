package com.example.mangacat.data.dto.chapter

data class ChapterAttributes(
    val volume: String,
    val chapter: String,
    val title: String?,
    val translatedLanguage: String,
    val pages: Int
)
