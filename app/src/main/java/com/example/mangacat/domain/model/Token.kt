package com.example.mangacat.domain.model

import com.example.mangacat.data.dto.authentication.AuthResponse

data class Token(
    val accessToken: String?,
    val refreshToken: String?
) {
    constructor(token: AuthResponse): this(
        accessToken = token.accessToken,
        refreshToken = token.refreshToken
    )
}
