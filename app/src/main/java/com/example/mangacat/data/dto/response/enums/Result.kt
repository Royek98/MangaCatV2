package com.example.mangacat.data.dto.response.enums

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class Result {
    @SerialName("ok")
    OK,

    @SerialName("error")
    ERROR,
}