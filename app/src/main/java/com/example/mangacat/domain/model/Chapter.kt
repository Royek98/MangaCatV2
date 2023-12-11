package com.example.mangacat.domain.model

data class Chapter(
    val id: String,
    val mangaId: String,
    val volume: String,
    val chapter: String,
    val title: String?,
    val scanlationGroupName: String,
    val uploaderUsername: String,
    val updatedAt: String
)
