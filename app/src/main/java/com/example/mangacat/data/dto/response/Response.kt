package com.example.mangacat.data.dto.response

import com.example.mangacat.data.dto.response.enums.Result
import kotlinx.serialization.Serializable

@Serializable
data class EntityResponse<ATTRIBUTES, RELATIONSHIPS>(
    val result: Result,
    val response: String,
    val data: Data<ATTRIBUTES, RELATIONSHIPS>
)

@Serializable
data class CollectionResponse<ATTRIBUTES>(
    val result: Result,
    val response: String,
    val data: List<DataIncludes<ATTRIBUTES>>,
)