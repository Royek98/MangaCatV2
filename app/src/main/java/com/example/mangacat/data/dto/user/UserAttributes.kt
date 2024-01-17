package com.example.mangacat.data.dto.user

import kotlinx.serialization.Serializable

@Serializable
data class UserAttributes(
    val username: String,
    val roles: List<String>
)
