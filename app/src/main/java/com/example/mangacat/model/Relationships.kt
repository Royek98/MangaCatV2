package com.example.mangacat.model

import com.example.mangacat.model.response.enums.Type
import kotlinx.serialization.Serializable

@Serializable
data class Relationships(
    val id: String,
    val type: Type
)
