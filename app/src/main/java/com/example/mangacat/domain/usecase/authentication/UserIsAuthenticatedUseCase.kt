package com.example.mangacat.domain.usecase.authentication

import com.example.mangacat.data.dto.authentication.IsAuthenticatedResponse
import com.example.mangacat.data.dto.response.ErrorResponse
import com.example.mangacat.data.network.Resource
import com.example.mangacat.domain.repository.AuthRepository
import com.example.mangacat.utils.IOHttpCustomException
import retrofit2.HttpException
import javax.inject.Inject
import kotlin.jvm.Throws

class UserIsAuthenticatedUseCase@Inject constructor(
    private val repository: AuthRepository
) {
//    @Throws(IOHttpCustomException::class)
    suspend operator fun invoke(token: String): IsAuthenticatedResponse {
        return try {
            repository.check(token)
        } catch (e: HttpException) {
            throw IOHttpCustomException(ErrorResponse(e).getMessages())
        }
    }

//    suspend operator fun invoke(token: String) {
//        return repository.clear()
//    }
}