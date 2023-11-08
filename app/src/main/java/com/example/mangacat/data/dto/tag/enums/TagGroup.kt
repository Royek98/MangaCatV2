package com.example.mangacat.data.dto.tag.enums

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class TagGroup {
    @SerialName("content")
    CONTENT,

    @SerialName("format")
    FORMAT,

    @SerialName("genre")
    GENRE,

    @SerialName("theme")
    THEME
}