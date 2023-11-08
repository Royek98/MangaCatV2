package com.example.mangacat.data.dto.manga.enums

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class Status {
    @SerialName("completed")
    COMPLETED,

    @SerialName("ongoing")
    ONGOING,

    @SerialName("hiatus")
    HIATUS,

    @SerialName("cancelled")
    CANCELLED
}
