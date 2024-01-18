package com.example.mangacat.ui.screens.login.components

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mangacat.domain.model.User
import com.example.mangacat.domain.usecase.authentication.GetAuthenticatedUserInformationUseCase
import com.example.mangacat.domain.usecase.authentication.GetTokenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileContentViewModel @Inject constructor(
    private val getAuthenticatedUserInformationUseCase: GetAuthenticatedUserInformationUseCase,
    private val getTokenUseCase: GetTokenUseCase
): ViewModel() {

    private val _user = MutableStateFlow(User("", "", listOf()))
    val user: StateFlow<User> = _user

    init {
        setUser()
    }

    fun setUser() {
        viewModelScope.launch {
            getTokenUseCase.invoke().collect {
                //toDo handle token null properly
                if (it.accessToken != null) {
                    val response = getAuthenticatedUserInformationUseCase.invoke(it)
                    _user.value = _user.value.copy(
                        id = response?.data?.id,
                        username = response?.data?.attributes?.username,
                        roles = response?.data?.attributes?.roles
                    )
                }
            }
        }
    }
}