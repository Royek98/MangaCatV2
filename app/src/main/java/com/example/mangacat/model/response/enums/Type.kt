package com.example.mangacat.model.response.enums

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class Type {
    @SerialName("manga")
    MANGA,

    @SerialName("user")
    USER,

    @SerialName("custom_list")
    CUSTOM_LIST,

    @SerialName("tag")
    TAG,

    @SerialName("author")
    AUTHOR,

    @SerialName("artist")
    ARTIST,

    @SerialName("cover_art")
    COVER_ART,
}