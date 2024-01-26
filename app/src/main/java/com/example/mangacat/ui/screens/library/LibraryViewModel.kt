package com.example.mangacat.ui.screens.library

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mangacat.data.dto.libraryStatus.LibraryResponse
import com.example.mangacat.data.network.Resource
import com.example.mangacat.domain.usecase.library.GetLibraryStatusesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class LibraryViewModel @Inject constructor(
    private val getLibraryStatusesUseCase: GetLibraryStatusesUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow<Resource<LinkedHashMap<String, MutableList<String>>>>(Resource.Loading)
    val uiState: StateFlow<Resource<LinkedHashMap<String, MutableList<String>>>> = _uiState

    init {
        getStatuses()
    }

    fun getStatuses() {
        viewModelScope.launch {
            _uiState.value = Resource.Loading

            _uiState.value = try {
                val resposne = getLibraryStatusesUseCase()
                Resource.Success(resposne)
            } catch (e: IOException) {
                Resource.Error()
            }

        }
    }
}