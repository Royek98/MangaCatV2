package com.example.mangacat.data.dto.read

import kotlinx.serialization.Serializable


@Serializable
data class Chapter(
    val hash: String,
    val data: List<String>,
    val dataSaver: List<String>
)

@Serializable
data class ReadResponse(
    val result: String,
    val baseUrl: String,
    val chapter: Chapter
)
