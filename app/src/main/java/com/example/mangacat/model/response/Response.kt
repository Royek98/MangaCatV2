package com.example.mangacat.model.response

import com.example.mangacat.model.response.enums.Result
import kotlinx.serialization.Serializable

@Serializable
data class Response<ATTRIBUTES, RELATIONSHIPS>(
    val result: Result,
    val response: String,
    val data: Data<ATTRIBUTES, RELATIONSHIPS>
)
