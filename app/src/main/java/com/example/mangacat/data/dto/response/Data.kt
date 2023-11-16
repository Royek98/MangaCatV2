package com.example.mangacat.data.dto.response

import com.example.mangacat.data.dto.Includes
import com.example.mangacat.data.dto.IncludesResponseSerializer
import com.example.mangacat.data.dto.response.enums.Type
import kotlinx.serialization.Serializable

@Serializable
data class Data<ATTRIBUTES, RELATIONSHIPS>(
    val id: String,
    val type: Type,
    val attributes: ATTRIBUTES,
    val relationships: RELATIONSHIPS
)


@Serializable
data class DataType2<ATTRIBUTES>(
    val id: String,
    val type: Type,
    val attributes: ATTRIBUTES,
    @Serializable(with = IncludesResponseSerializer::class)
    val relationships: List<Includes>? = null
)

