package com.example.mangacat.data.dto.chapter

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("attributes")
data class ChapterAttributes(
    val volume: String,
    val chapter: String,
    val title: String?,
    val translatedLanguage: String,
    val pages: Int
)
