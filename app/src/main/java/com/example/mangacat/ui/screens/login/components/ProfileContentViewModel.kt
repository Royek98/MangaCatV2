package com.example.mangacat.ui.screens.login.components

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mangacat.data.dto.ScanlationGroupIncludes
import com.example.mangacat.data.dto.response.Data
import com.example.mangacat.data.dto.response.EntityResponse
import com.example.mangacat.data.dto.user.UserAttributes
import com.example.mangacat.data.network.Resource
import com.example.mangacat.domain.model.User
import com.example.mangacat.domain.usecase.authentication.GetAuthenticatedUserInformationUseCase
import com.example.mangacat.domain.usecase.authentication.GetTokenUseCase
import com.example.mangacat.domain.usecase.authentication.RefreshUseCase
import com.example.mangacat.domain.usecase.authentication.UserIsAuthenticatedUseCase
import com.example.mangacat.utils.IOHttpCustomException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class ProfileContentViewModel @Inject constructor(
    private val getAuthenticatedUserInformationUseCase: GetAuthenticatedUserInformationUseCase,
    private val getTokenUseCase: GetTokenUseCase,
    private val userIsAuthenticatedUseCase: UserIsAuthenticatedUseCase,
    private val refreshUseCase: RefreshUseCase,
    private val clearTokenUseCase: GetTokenUseCase
) : ViewModel() {

    private val _user = MutableStateFlow<Resource<User>>(Resource.Loading)
    val user: StateFlow<Resource<User>> = _user

    init {
        setUser()
    }

    // I have already checked if user is authenticated in AuthViewModel
    // this viemodel is initialized when the state is true, i don't have to check it again
    fun setUser() {
        viewModelScope.launch {
            var isAuthenticated = false
//            var accessToken: String? = null

            getTokenUseCase.invoke().let { token ->
                _user.value = try {
                    token?.accessToken?.let {
                        Resource.Success(getAuthenticatedUserInformationUseCase.invoke(it))
                    } ?: Resource.Error(listOf("Access Token is empty"))
                } catch (e: IOHttpCustomException) {
//                        clearTokenUseCase()
                    Resource.Error(e.messages)
                }
            }
        }
    }
}