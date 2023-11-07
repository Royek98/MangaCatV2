package com.example.mangacat.model.response.enums

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class Result {
    @SerialName("ok")
    OK,

    @SerialName("error")
    ERROR,
}