package com.example.mangacat.model

import kotlinx.serialization.Serializable

@Serializable
data class Data<ATTRIBUTES, RELATIONSHIPS>(
    val id: String,
    val type: Type,
    val attributes: ATTRIBUTES,
    val relationships: ArrayList<RELATIONSHIPS>
)
