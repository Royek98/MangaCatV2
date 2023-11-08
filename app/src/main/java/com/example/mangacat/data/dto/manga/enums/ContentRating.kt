package com.example.mangacat.data.dto.manga.enums

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class ContentRating {
    @SerialName("safe")
    SAFE,

    @SerialName("suggestive")
    SUGGESTIVE,

    @SerialName("erotica")
    EROTICA,

    @SerialName("pornographic")
    PORNOGRAPHIC
}