package com.example.mangacat.domain.usecase.authentication

import com.example.mangacat.data.dto.authentication.IsAuthenticatedResponse
import com.example.mangacat.data.network.Resource
import com.example.mangacat.domain.repository.AuthRepository
import javax.inject.Inject

class UserIsAuthenticatedUseCase@Inject constructor(
    private val repository: AuthRepository
) {
    suspend fun invoke(): Resource<IsAuthenticatedResponse> {
        return repository.check()
    }
}