package com.example.mangacat.model.mangaList

import kotlinx.serialization.Serializable

@Serializable
data class Attributes(
    val name: String? = null,
    val visibility : String,
    val version: Int
)
