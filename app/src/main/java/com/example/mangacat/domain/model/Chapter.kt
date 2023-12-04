package com.example.mangacat.domain.model

data class Chapter(
    val volume: String,
    val chapter: String,
    val title: String?,
    val scanlationGroupName: String,
    val uploaderUsername: String,
    val updatedAt: String
)
