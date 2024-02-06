package com.example.mangacat.domain.usecase.authentication

import com.example.mangacat.data.dto.response.ErrorResponse
import com.example.mangacat.domain.model.Token
import com.example.mangacat.domain.repository.AuthRepository
import com.example.mangacat.utils.IOHttpCustomException
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import kotlin.jvm.Throws

class SignInSaveTokenUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    @Throws(IOHttpCustomException::class)
    suspend operator fun invoke(username: String, password: String) {
        try {
            val tokenResponse = repository.authenticate(username, password)

            repository.saveTokenLocally(Token(tokenResponse))
        } catch (e: HttpException) {
            throw IOHttpCustomException(ErrorResponse(e).getMessages())
        } catch (e: IOException) {
            throw IOHttpCustomException(listOf("${e.message}"))
        }
    }
}