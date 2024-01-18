package com.example.mangacat.data.dto.authentication

import kotlinx.serialization.Serializable

@Serializable
data class IsAuthenticatedResponse(
    val result: String,
    val isAuthenticated: Boolean
)
