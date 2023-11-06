package com.example.mangacat.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class Type {
    @SerialName("manga")
    MANGA,

    @SerialName("user")
    USER,

    @SerialName("custom_list")
    CUSTOM_LIST
}