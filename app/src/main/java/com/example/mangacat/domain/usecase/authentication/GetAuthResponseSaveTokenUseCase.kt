package com.example.mangacat.domain.usecase.authentication

import com.example.mangacat.data.dto.authentication.AuthResponse
import com.example.mangacat.data.network.Resource
import com.example.mangacat.domain.repository.AuthRepository
import javax.inject.Inject

class GetAuthResponseSaveTokenUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(username: String, password: String): Resource<AuthResponse> {
        return repository.authenticate(username, password)
    }
}