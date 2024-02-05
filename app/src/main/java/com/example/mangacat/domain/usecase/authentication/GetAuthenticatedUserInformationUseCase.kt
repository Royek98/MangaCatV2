package com.example.mangacat.domain.usecase.authentication;

import com.example.mangacat.data.dto.ScanlationGroupIncludes
import com.example.mangacat.data.dto.response.Data
import com.example.mangacat.data.dto.response.EntityResponse
import com.example.mangacat.data.dto.user.UserAttributes
import com.example.mangacat.data.network.Resource
import com.example.mangacat.domain.model.Token
import com.example.mangacat.domain.model.User
import com.example.mangacat.domain.repository.AuthRepository;

import javax.inject.Inject;

public class GetAuthenticatedUserInformationUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(token: String): User {
        val response = repository.getAuthenticatedUserInformation(token)
        return User(
            id = response.data.id,
            username = response.data.attributes.username,
            roles = response.data.attributes.roles
        )
    }
}
