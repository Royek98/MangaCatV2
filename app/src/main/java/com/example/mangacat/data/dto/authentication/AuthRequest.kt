package com.example.mangacat.data.dto.authentication

import com.example.mangacat.utils.AppConstants
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AuthRequest(
    @SerialName("grant_type")
    val grantType: String = "password",
    val username: String,
    val password: String,
    @SerialName("client_id")
    val clientId: String = AppConstants.clientId,
    @SerialName("client_secret")
    val clientSecret: String = AppConstants.clientSecret
)
