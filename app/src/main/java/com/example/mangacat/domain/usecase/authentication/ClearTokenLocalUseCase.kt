package com.example.mangacat.domain.usecase.authentication

import com.example.mangacat.domain.repository.AuthRepository
import javax.inject.Inject

class ClearTokenLocalUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke() {
        repository.clearToken()
    }
}