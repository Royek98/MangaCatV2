package com.example.mangacat.data.repository

import com.example.mangacat.data.dto.ScanlationGroupIncludes
import com.example.mangacat.data.dto.authentication.AuthRequest
import com.example.mangacat.data.dto.authentication.AuthResponse
import com.example.mangacat.data.dto.authentication.IsAuthenticatedResponse
import com.example.mangacat.data.dto.authentication.RefreshRequest
import com.example.mangacat.data.dto.response.Data
import com.example.mangacat.data.dto.response.EntityResponse
import com.example.mangacat.data.dto.user.UserAttributes
import com.example.mangacat.data.local.AuthPreferences
import com.example.mangacat.data.network.AuthApiService
import com.example.mangacat.data.network.MangaDexApiService
import com.example.mangacat.domain.model.Token
import com.example.mangacat.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl(
    private val preferences: AuthPreferences,
    private val authService: AuthApiService,
    private val mangaDexService: MangaDexApiService
) : AuthRepository {
    override suspend fun authenticate(username: String, password: String): AuthResponse {
        val request = AuthRequest(
            username = username,
            password = password
        )
        return authService.authenticate(
            grantType = request.grantType,
            username = request.username,
            password = request.password,
            clientId = request.clientId,
            clientSecret = request.clientSecret
        )
    }

    override suspend fun refresh(refreshToken: String): AuthResponse {
        val request = RefreshRequest(refreshToken)

        return authService.refresh(
            grantType = request.grantType,
            refreshToken = request.refreshToken,
            clientId = request.clientId,
            clientSecret = request.clientSecret
        )
    }

    override suspend fun check(token: String): IsAuthenticatedResponse {
        return mangaDexService.check("Bearer $token")
    }

    override suspend fun getToken(): Token? {
        return preferences.getAuthToken()
    }

    override suspend fun getAuthenticatedUserInformation(accessToken: String):
            EntityResponse<Data<UserAttributes, List<ScanlationGroupIncludes>>> {
        return mangaDexService.getAuthenticatedUserInformation("Bearer $accessToken}")
    }

    override suspend fun clearToken() {
        preferences.clearAuthToken()
    }

    override suspend fun saveTokenLocally(token: Token) {
        preferences.saveAuthToken(
            AuthResponse(
                accessToken = token.accessToken,
                refreshToken = token.refreshToken
            )
        )
    }
}