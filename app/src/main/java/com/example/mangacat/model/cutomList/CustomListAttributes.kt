package com.example.mangacat.model.cutomList

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("attributes")
data class CustomListAttributes(
    val name: String,
    val visibility : String,
    val version: Int
)
