package com.example.mangacat.ui.screens.home

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mangacat.data.dto.manga.enums.ContentRating
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
) : ViewModel() {

    var homeUiState: Resource<List<String>> by mutableStateOf(
        Resource.Loading
    )
        private set

    init {
        getSeasonalManga()
    }

    fun getSeasonalManga() {
        viewModelScope.launch {
            homeUiState = Resource.Loading

            homeUiState = try {
                val ids = mangaDexRepository.getSeasonalMangaIds()
                Log.d("test", "getSeasonalManga: $ids")

                val listOfRelationships = ids.data.relationships
                val listOfIds = listOfRelationships.map { it.id }
                val test = mangaDexRepository.getMangaListByIds(
                    10,
                    0,
                    listOf("cover_art", "author"),
                    listOf(
                        ContentRating.SAFE,
                        ContentRating.SUGGESTIVE,
                        ContentRating.EROTICA,

                        ),
                    listOfIds
                )

                Log.d("test", "getSeasonalManga: $listOfIds")

                Resource.Success(listOfIds)
            } catch (e: IOException) {
                Resource.Error
            }
        }
    }
}