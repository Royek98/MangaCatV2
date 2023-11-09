package com.example.mangacat.ui.screens.home

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mangacat.data.dto.response.EntityResponse
import com.example.mangacat.data.dto.response.enums.Type
import com.example.mangacat.data.dto.cutomList.CustomListAttributes
import com.example.mangacat.data.dto.Relationships
import com.example.mangacat.data.dto.manga.enums.ContentRating
import com.example.mangacat.data.network.Resource
import com.example.mangacat.domain.model.HomeSeasonalMangaItem
import com.example.mangacat.domain.repository.MangaDexRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
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

    var homeUiState: Resource<List<HomeSeasonalMangaItem>> by mutableStateOf(
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

                val listOfRelationships = ids.data.relationships
                val listOfIds = listOfRelationships.map { it.id }
                Log.d("test", "getSeasonalManga: $listOfIds")
                val test = mangaDexRepository.getMangaListByIds(
                    10,
                    0,
                    listOf(""),
                    listOf(
                        ContentRating.SAFE,
                        ContentRating.SUGGESTIVE,
                        ContentRating.EROTICA,

                        ),
                    listOfIds
                )

                val finalList = test.data.map { it ->
                    HomeSeasonalMangaItem(
                        id = it.id,
                        cover = "",
                        tags = listOf(it.attributes.publicationDemographic!!.name, it.attributes.contentRating.name, it.attributes.tags[0].attributes.name.en)
                    )
                }



                Log.d("test", "getSeasonalManga: $finalList")

                Resource.Success(finalList)
            } catch (e: IOException) {
                Resource.Error
            }
        }
    }
}