package com.example.mangacat.model.mangaList

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Relationships(
    @SerialName(value = "id")
    val id: String,

    @SerialName(value = "type")
    val type: String
)
