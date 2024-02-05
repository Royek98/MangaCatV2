package com.example.mangacat.data.dto.authentication

import com.example.mangacat.utils.AppConstants
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
open class Request(
    @SerialName("grant_type")
    open val grantType: String,
    @SerialName("client_id")
    val clientId: String = AppConstants.clientId,
    @SerialName("client_secret")
    val clientSecret: String = AppConstants.clientSecret
)

@Serializable
data class AuthRequest(
    val username: String,
    val password: String,
    override val grantType: String = "password"
): Request(grantType)

@Serializable
data class RefreshRequest(
    val refreshToken: String,
    override val grantType: String = "refresh_token"
): Request(grantType)