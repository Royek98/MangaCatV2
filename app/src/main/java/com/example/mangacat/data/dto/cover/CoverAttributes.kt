package com.example.mangacat.data.dto.cover

import kotlinx.serialization.Serializable

@Serializable
data class CoverAttributes(
    val fileName: String,
    val volume: String?
)
