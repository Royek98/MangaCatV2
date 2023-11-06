package com.example.mangacat.model.mangaList

import com.example.mangacat.model.Data
import kotlinx.serialization.Serializable

@Serializable
data class MangaList(
    val result: String,
    val response: String,
    val data: Data<Attributes, Relationships>
)
