package com.example.mangacat.data.dto.response

import com.example.mangacat.data.dto.response.enums.Type
import kotlinx.serialization.Serializable

@Serializable
data class Data<ATTRIBUTES, RELATIONSHIPS>(
    val id: String,
    val type: Type,
    val attributes: ATTRIBUTES,
    val relationships: List<RELATIONSHIPS>
)
