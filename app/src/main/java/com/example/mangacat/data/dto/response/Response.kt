package com.example.mangacat.data.dto.response

import com.example.mangacat.data.dto.response.enums.Result
import com.example.mangacat.di.appJson
import kotlinx.serialization.Serializable
import retrofit2.HttpException

@Serializable
data class EntityResponse<DATA>(
    val result: Result,
    val response: String,
    val data: DATA
)

@Serializable
data class CollectionResponse<ATTRIBUTES>(
    val result: Result,
    val response: String,
    val data: List<DataIncludes<ATTRIBUTES>>,
)

@Serializable
data class CollectionResponseNotIncludes<ATTRIBUTES>(
    val result: Result,
    val response: String,
    val data: List<ATTRIBUTES>,
)

@Serializable
data class ErrorResponse(
    var result: String,
    var errors: List<ErrorMessage>
) {
    constructor(e: HttpException) : this(
        result = "",
        errors = emptyList()
    ) {
        val errorResponseBody = e.response()?.errorBody()?.string()?.let {
            appJson.decodeFromString<ErrorResponse>(it)
        }

        if (errorResponseBody != null) {
            this.result = errorResponseBody.result
            this.errors = errorResponseBody.errors
        }
    }

    fun getMessages(): List<String> = errors.map { it.detail }
}

@Serializable
data class ErrorMessage(
    val id: String,
    val status: Int,
    val title: String,
    val detail: String
)
