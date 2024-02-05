package com.example.mangacat.ui.screens.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mangacat.data.network.Resource
import com.example.mangacat.domain.usecase.authentication.AuthenticationUseCase
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
//    private val userIsAuthenticatedUseCase: UserIsAuthenticatedUseCase,
//    private val getTokenUseCase: GetTokenUseCase,
//    private val refreshUseCase: RefreshUseCase
    private val authenticationUseCase: AuthenticationUseCase
): ViewModel() {
    private val _isAuthenticated = MutableStateFlow<Resource<Boolean>>(Resource.Loading)
    val isAuthenticated: StateFlow<Resource<Boolean>> = _isAuthenticated

    init {
        viewModelScope.launch {
            checkUserIsAuthenticated()
//            clear()
        }
    }

    suspend fun checkUserIsAuthenticated() {
        _isAuthenticated.value = Resource.Success(authenticationUseCase())
    }

//    private suspend fun clear() {
//        userIsAuthenticatedUseCase.invoke("")
//    }
}