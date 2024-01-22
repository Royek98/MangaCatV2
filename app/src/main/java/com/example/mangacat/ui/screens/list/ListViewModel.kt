package com.example.mangacat.ui.screens.list;

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mangacat.data.network.Resource
import com.example.mangacat.domain.model.MangaListItem
import com.example.mangacat.domain.usecase.list.GetStaffPicksUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val getStaffPicksUseCase: GetStaffPicksUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow<Resource<List<MangaListItem>>>(Resource.Loading)
    val uiState: StateFlow<Resource<List<MangaListItem>>> = _uiState

    fun setStaffPicks() {
        viewModelScope.launch {
            _uiState.value = Resource.Loading

            _uiState.value = try {
                Resource.Success(getStaffPicksUseCase())
            } catch (e: IOException) {
                Resource.Error()
            }
        }
    }

    fun setRecentlyAdded() {
        viewModelScope.launch {
            _uiState.value = Resource.Loading

            _uiState.value = try {
                Resource.Success(getStaffPicksUseCase())
            } catch (e: IOException) {
                Resource.Error()
            }
        }
    }

}
