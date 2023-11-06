package com.example.mangacat.model.mangaList

import com.example.mangacat.model.Type
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Relationships(
    @SerialName(value = "id")
    val id: String,

    @SerialName(value = "type")
    val type: Type
)
