package com.example.mangacat.domain.usecase.authentication

import com.example.mangacat.data.dto.authentication.AuthResponse
import com.example.mangacat.data.network.Resource
import com.example.mangacat.domain.repository.AuthRepository
import javax.inject.Inject

class GetAuthResponseSaveTokenUseCase @Inject constructor(
    private val repository: AuthRepository
) {

    // toDo move this try catch to RepositoryImpl
    suspend operator fun invoke(username: String, password: String): Resource<AuthResponse> {
//        return try {
//            delay(2000)
//            val response = repository.authenticate(username, password)
//            Resource.Success(Token(
//                accessToken = response.accessToken,
//                refreshToken = response.refreshToken
//            ))
//        } catch (e: IOException){
//            Resource.Error("${e.message}")
//        }catch (e: HttpException){
//            Resource.Error("${e.message}")
//        }
        return repository.authenticate(username, password)
    }
}