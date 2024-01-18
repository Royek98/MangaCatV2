package com.example.mangacat.ui.screens.login.components

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mangacat.data.network.Resource
import com.example.mangacat.domain.usecase.authentication.GetAuthResponseSaveTokenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginFormViewModel @Inject constructor(
    private val getAuthResponseSaveTokenUseCase: GetAuthResponseSaveTokenUseCase
//    private val getTokenUseCase: GetTokenUseCase
) : ViewModel() {
    private val _username = MutableStateFlow("")
    val username: StateFlow<String> = _username

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password

    private val _visibility = MutableStateFlow(false)
    val visibility: StateFlow<Boolean> = _visibility

    private val _credentialsError = MutableStateFlow(Pair(false, ""))
    val credentialsError: StateFlow<Pair<Boolean, String>> = _credentialsError

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

//    private val _token = MutableStateFlow<Token>(Token(null, null))
//    val token: StateFlow<Token> = _token

    fun setUsername(newValue: String) {
        _username.update { newValue }
    }

    fun setPassword(newValue: String) {
        _password.update { newValue }
    }

    fun setVisibility() {
        _visibility.update { !it }
    }

    fun authenticate(navigateToHome: () -> Unit) {
        viewModelScope.launch {
            _credentialsError.value = _credentialsError.value.copy(first = false)
            _isLoading.value = true
            when(val response = getAuthResponseSaveTokenUseCase.invoke(_username.value, _password.value)) {
                is Resource.Loading -> {}
                is Resource.Success -> {
                    _isLoading.value = false
                    //toDo navigate to home route
                    navigateToHome()
                }
                is Resource.Error -> {
                    _isLoading.value = false
                    _credentialsError.value =
                        _credentialsError.value.copy(first = true, second = response.message)
                }
            }
        }
    }
//    fun getToken() {
//        viewModelScope.launch{
//            getTokenUseCase.invoke().collect {
//                _token.value = it
//            }
//        }
//    }
}