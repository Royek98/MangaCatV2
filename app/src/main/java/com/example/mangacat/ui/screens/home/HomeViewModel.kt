package com.example.mangacat.ui.screens.home

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mangacat.data.dto.response.Response
import com.example.mangacat.data.dto.response.enums.Type
import com.example.mangacat.data.dto.cutomList.CustomListAttributes
import com.example.mangacat.data.dto.Relationships
import com.example.mangacat.data.network.Resource
import com.example.mangacat.domain.repository.MangaDexRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

//sealed interface HomeUiState {
//    data class Success(val mangaIdList: Response<CustomListAttributes, Relationships>) : HomeUiState
//    object Error : HomeUiState
//    object Loading : HomeUiState
//}

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val mangaDexRepository: MangaDexRepository,
): ViewModel() {

    var homeUiState: Resource<Response<CustomListAttributes, Relationships>> by mutableStateOf(Resource.Loading)
        private set

    init {
        getSeasonalManga()
    }

    fun getSeasonalManga() {
        viewModelScope.launch {
            homeUiState = Resource.Loading

            homeUiState = try {
                val ids = mangaDexRepository.getSeasonalMangaIds()

                val getIdLimit10 = ids.data.relationships.filter {
                    it.type == Type.MANGA
                }.subList(0, 10)
                Log.d("test", "getSeasonalManga: $getIdLimit10")

                val test = mangaDexRepository.getMangaById()
                Log.d("test", "getSeasonalManga: $test")

                Resource.Success(ids)
            } catch (e: IOException) {
                Resource.Error
            }
        }
    }
}