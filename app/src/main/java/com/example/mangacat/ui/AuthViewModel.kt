package com.example.mangacat.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mangacat.data.network.Resource
import com.example.mangacat.domain.usecase.authentication.GetTokenUseCase
import com.example.mangacat.domain.usecase.authentication.UserIsAuthenticatedUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val userIsAuthenticatedUseCase: UserIsAuthenticatedUseCase
) : ViewModel() {
    private val _isAuthenticated = MutableStateFlow(false)
    val isAuthenticated: StateFlow<Boolean> = _isAuthenticated

    suspend fun checkUserIsAuthenticated() {
        when (val result = userIsAuthenticatedUseCase.invoke()) {
            is Resource.Success -> {
                _isAuthenticated.value = result.data.isAuthenticated
            }

            else -> {
                _isAuthenticated.value = false
            }
        }
    }

    init {
        viewModelScope.launch {
            checkUserIsAuthenticated()
        }
    }
}