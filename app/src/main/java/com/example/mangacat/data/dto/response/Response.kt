package com.example.mangacat.data.dto.response

import android.util.Log
import com.example.mangacat.data.dto.response.enums.Result
import com.example.mangacat.di.appJson
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import org.json.JSONException
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
        var errorResponseBody: ErrorResponse? = ErrorResponse(result = "", errors = emptyList())
        try {
            errorResponseBody = e.response()?.errorBody()?.string()?.let {
                appJson.decodeFromString<ErrorResponse>(it)
            }
        } catch (e: SerializationException) {
            if (e.message?.contains("<!doctype html>") == true) {
                errorResponseBody?.result = "maintenance"
                errorResponseBody?.errors =
                    listOf(
                        ErrorMessage(
                            id = "",
                            status = 0,
                            title = "Maintenance",
                            detail = "MangaDex is temporarily down for maintenance."
                        )
                    )
            } else {
                errorResponseBody?.result = "unknown"
                errorResponseBody?.errors =
                    listOf(
                        ErrorMessage(
                            id = "",
                            status = 0,
                            title = "Unknown",
                            detail = e.message ?: ""
                        )
                    )
            }

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
