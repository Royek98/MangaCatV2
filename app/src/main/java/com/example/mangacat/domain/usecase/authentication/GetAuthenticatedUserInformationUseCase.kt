package com.example.mangacat.domain.usecase.authentication;

import com.example.mangacat.data.dto.ScanlationGroupIncludes
import com.example.mangacat.data.dto.response.Data
import com.example.mangacat.data.dto.response.EntityResponse
import com.example.mangacat.data.dto.user.UserAttributes
import com.example.mangacat.domain.model.Token
import com.example.mangacat.domain.repository.AuthRepository;
import kotlinx.coroutines.flow.collect

import javax.inject.Inject;

public class GetAuthenticatedUserInformationUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(token: Token):
            EntityResponse<Data<UserAttributes, List<ScanlationGroupIncludes>>>? {
        return token.accessToken?.let { repository.getAuthenticatedUserInformation(it) }
    }
}
