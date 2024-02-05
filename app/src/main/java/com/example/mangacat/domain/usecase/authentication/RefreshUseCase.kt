package com.example.mangacat.domain.usecase.authentication

import android.util.Log
import com.example.mangacat.data.dto.response.ErrorResponse
import com.example.mangacat.domain.model.Token
import com.example.mangacat.domain.repository.AuthRepository
import com.example.mangacat.utils.IOHttpCustomException
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import kotlin.jvm.Throws

class RefreshUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    @Throws(IOHttpCustomException::class)
    suspend operator fun invoke(refreshToken: String): String {
        try {
            val responseToken = repository.refresh(refreshToken)

            repository.saveTokenLocally(Token(responseToken))
            return responseToken.accessToken!!
        } catch (e: HttpException) {
            throw IOHttpCustomException(ErrorResponse(e).getMessages())
        } catch (e: IOException) {
            throw IOHttpCustomException(listOf("${e.message}"))
        } catch (e: NullPointerException) {
            throw IOHttpCustomException(listOf("${e.message}"))
        }
    }
}