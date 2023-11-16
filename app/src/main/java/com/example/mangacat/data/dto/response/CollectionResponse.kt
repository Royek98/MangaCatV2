package com.example.mangacat.data.dto.response

import com.example.mangacat.data.dto.response.enums.Result
import kotlinx.serialization.Serializable

@Serializable
data class CollectionResponse<ATTRIBUTES>(
    val result: Result,
    val response: String,
    val data: List<DataType2<ATTRIBUTES>>,
)
