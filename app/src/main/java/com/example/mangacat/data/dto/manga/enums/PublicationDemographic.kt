package com.example.mangacat.data.dto.manga.enums

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class PublicationDemographic {
    @SerialName("shounen")
    SHOUNEN,

    @SerialName("shoujo")
    SHOUJO,

    @SerialName("josei")
    JOSEI,

    @SerialName("seinen")
    SEINEN,

    @SerialName("none")
    NONE

}