package com.example.mangacat.domain.usecase.authentication

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import com.example.mangacat.data.network.Resource
import com.example.mangacat.domain.model.Token
import com.example.mangacat.utils.IOHttpCustomException
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class AuthenticationUseCase @Inject constructor(
    private val userIsAuthenticatedUseCase: UserIsAuthenticatedUseCase,
    private val getTokenUseCase: GetTokenUseCase,
    private val refreshUseCase: RefreshUseCase
) {
    suspend operator fun invoke(): Boolean {
        val isAuthenticated = mutableStateOf(false)
        val token = getTokenUseCase.invoke()

        isAuthenticated.value = try {
            token?.accessToken?.let { access ->
                val result = userIsAuthenticatedUseCase.invoke(access)
                result.isAuthenticated
            } ?: false
        } catch (e: IOHttpCustomException) {
            // if user is not authenticated it returns error 401 (Unauthorized)
            refresh(token?.refreshToken)
        }

        return isAuthenticated.value
    }

    private suspend fun refresh(refreshToken: String?): Boolean {
        refreshToken?.let { refresh ->
            return try {
                val newToken = refreshUseCase.invoke(refresh)
                val refreshedResult = userIsAuthenticatedUseCase.invoke(newToken)
                refreshedResult.isAuthenticated
            } catch (e: IOHttpCustomException) {
                false
            }
        }
        return false
    }
}