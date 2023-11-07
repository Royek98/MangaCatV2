package com.example.mangacat.model.response

import com.example.mangacat.model.response.enums.Type
import kotlinx.serialization.Serializable

@Serializable
data class Data<ATTRIBUTES, RELATIONSHIPS>(
    val id: String,
    val type: Type,
    val attributes: ATTRIBUTES,
    val relationships: List<RELATIONSHIPS>
)
