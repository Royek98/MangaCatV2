package com.example.mangacat.data.dto.response

import android.util.Log
import com.example.mangacat.data.dto.response.enums.Result
import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

@Serializable
data class EntityResponse<DATA>(
    val result: Result,
    val response: String,
//    val data: Data<ATTRIBUTES, RELATIONSHIPS>
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
data class Error(
    val result: String,
    val errors: List<ErrorMessage>
)

@Serializable
data class ErrorMessage(
    val id: String,
    val status: Int,
    val title: String,
    val detail: String
)
