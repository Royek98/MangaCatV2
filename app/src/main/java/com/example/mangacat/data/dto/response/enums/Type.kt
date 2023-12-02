package com.example.mangacat.data.dto.response.enums

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

    @SerialName("creator")
    CREATOR,

    @SerialName("chapter")
    CHAPTER,

    @SerialName("scanlation_group")
    SCANLATION_GROUP
}