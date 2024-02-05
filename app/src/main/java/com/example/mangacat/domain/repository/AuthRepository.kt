package com.example.mangacat.domain.repository

import com.example.mangacat.data.dto.ScanlationGroupIncludes
import com.example.mangacat.data.dto.authentication.AuthResponse
import com.example.mangacat.data.dto.authentication.IsAuthenticatedResponse
import com.example.mangacat.data.dto.response.Data
import com.example.mangacat.data.dto.response.EntityResponse
import com.example.mangacat.data.dto.user.UserAttributes
import com.example.mangacat.domain.model.Token
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun authenticate(username: String, password: String): AuthResponse

    suspend fun refresh(refreshToken: String): AuthResponse

    suspend fun check(token: String): IsAuthenticatedResponse

    suspend fun getToken(): Token?

    suspend fun getAuthenticatedUserInformation(accessToken: String):
            EntityResponse<Data<UserAttributes, List<ScanlationGroupIncludes>>>

    suspend fun clearToken()

    suspend fun saveTokenLocally(token: Token)

}