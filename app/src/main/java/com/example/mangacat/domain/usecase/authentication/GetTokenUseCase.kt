package com.example.mangacat.domain.usecase.authentication

import android.util.Log
import com.example.mangacat.domain.model.Token
import com.example.mangacat.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

class GetTokenUseCase@Inject constructor(
    private val repository: AuthRepository
) {
    suspend fun invoke(): Flow<Token> {
        return  repository.getToken()
    }
}