package com.example.mangacat.ui.screens.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mangacat.data.network.Resource
import com.example.mangacat.domain.model.HomeSeasonalMangaItem
import com.example.mangacat.domain.usecase.home.GetSeasonalUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getSeasonalUseCase: GetSeasonalUseCase,
) : ViewModel() {

    var homeUiState: Resource<List<HomeSeasonalMangaItem>> by mutableStateOf(
        Resource.Loading
    )
        private set

//    private val _seasonalUiState = MutableStateFlow<Resource<List<HomeSeasonalMangaItem>>>(Resource.Loading)
//    val seasonalUiState: StateFlow<Resource<List<HomeSeasonalMangaItem>>> = _seasonalUiState.asStateFlow()
//

    init {
        getSeasonalManga()
    }

    fun getSeasonalManga() {
        viewModelScope.launch {
            homeUiState = Resource.Loading

            homeUiState = try {
                val result = getSeasonalUseCase()

                Resource.Success(result)
            } catch (e: IOException) {
                Resource.Error()
            }
        }
    }
}