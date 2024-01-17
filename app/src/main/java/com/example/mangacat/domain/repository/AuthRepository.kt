package com.example.mangacat.domain.repository

import com.example.mangacat.data.dto.authentication.AuthResponse
import com.example.mangacat.data.network.Resource
import com.example.mangacat.domain.model.Token
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun authenticate(username: String, password: String): Resource<AuthResponse>

    suspend fun getToken(): Flow<Token>

}