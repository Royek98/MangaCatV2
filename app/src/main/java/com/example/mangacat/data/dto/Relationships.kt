package com.example.mangacat.data.dto

import com.example.mangacat.data.dto.response.enums.Type
import kotlinx.serialization.Serializable

@Serializable
data class Relationships(
    val id: String,
    val type: Type
)
