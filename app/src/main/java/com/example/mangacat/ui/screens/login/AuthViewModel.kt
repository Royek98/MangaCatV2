package com.example.mangacat.ui.screens.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mangacat.data.network.Resource
import com.example.mangacat.domain.usecase.authentication.AuthenticationUseCase
import com.example.mangacat.domain.usecase.authentication.ClearTokenLocalUseCase
import com.example.mangacat.domain.usecase.authentication.GetTokenUseCase
import com.example.mangacat.domain.usecase.authentication.RefreshUseCase
import com.example.mangacat.domain.usecase.authentication.UserIsAuthenticatedUseCase
import com.example.mangacat.utils.IOHttpCustomException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authenticationUseCase: AuthenticationUseCase,
    private val clearTokenLocalUseCase: ClearTokenLocalUseCase,
    private val getTokenUseCase: GetTokenUseCase
) : ViewModel() {
    private val _isAuthenticated = MutableStateFlow<Resource<Boolean>>(Resource.Loading)
    val isAuthenticated: StateFlow<Resource<Boolean>> = _isAuthenticated

    init {
        checkUserIsAuthenticated()
    }

    fun checkUserIsAuthenticated() {
        viewModelScope.launch {
            _isAuthenticated.value = Resource.Success(authenticationUseCase())
        }
    }

    fun logout() {
        viewModelScope.launch {
            clearTokenLocalUseCase()
            val token = getTokenUseCase()
            if (token?.accessToken == null && token?.refreshToken == null) {
                _isAuthenticated.value = Resource.Success(false)
            } else {
                Resource.Error(listOf("Logout error: token is not empty"))
            }
        }
    }
}