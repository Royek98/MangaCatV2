package com.example.mangacat.ui.screens.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mangacat.data.dto.response.ErrorResponse
import com.example.mangacat.data.network.Resource
import com.example.mangacat.domain.model.HomeLatestUpdate
import com.example.mangacat.domain.model.HomeMangaItem
import com.example.mangacat.domain.model.HomeSeasonalMangaItem
import com.example.mangacat.domain.usecase.home.GetLatestUpdatesUseCase
import com.example.mangacat.domain.usecase.home.GetRecentlyAddedMangaUseCase
import com.example.mangacat.domain.usecase.home.GetSeasonalUseCase
import com.example.mangacat.domain.usecase.home.GetStaffPicksUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.json.JSONException
import retrofit2.HttpException
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getSeasonalUseCase: GetSeasonalUseCase,
    private val getStaffPicksUseCase: GetStaffPicksUseCase,
    private val getRecentlyAddedMangaUseCase: GetRecentlyAddedMangaUseCase,
    private val getLatestUpdatesUseCase: GetLatestUpdatesUseCase
) : ViewModel() {

    private val _seasonal =
        MutableStateFlow<Resource<List<HomeSeasonalMangaItem>>>(Resource.Loading)
    private val _feed = MutableStateFlow<Resource<List<HomeSeasonalMangaItem>>>(Resource.Loading)
    private val _staffPicks =
        MutableStateFlow<Resource<List<HomeMangaItem>>>(Resource.Loading)
    private val _recentlyAdded =
        MutableStateFlow<Resource<List<HomeMangaItem>>>(Resource.Loading)
    private val _latestUpdates =
        MutableStateFlow<Resource<List<HomeLatestUpdate>>>(Resource.Loading)

    val homeUiState: StateFlow<HomeUiState> =
        combine(
            _seasonal,
            _feed,
            _staffPicks,
            _recentlyAdded,
            _latestUpdates
        ) { seasonal, feed, staffPicks, recentlyAdded, latestUpdates ->
            HomeUiState(seasonal, feed, staffPicks, recentlyAdded, latestUpdates)
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            initialValue = HomeUiState()
        )

//    private val _seasonalUiState = MutableStateFlow<Resource<List<HomeSeasonalMangaItem>>>(Resource.Loading)
//    val seasonalUiState: StateFlow<Resource<List<HomeSeasonalMangaItem>>> = _seasonalUiState.asStateFlow()
//

    init {
        getSeasonalManga()
//        getFeed()
        getStaffPicks()
        getRecentlyAdded()
        getLatestUpdates()
    }

    fun getSeasonalManga() {
        viewModelScope.launch {
            _seasonal.value = Resource.Loading

            _seasonal.value = try {
                val result = getSeasonalUseCase()
                Resource.Success(result)
            } catch (e: HttpException) {
                Resource.Error(ErrorResponse(e).getMessages())
            }
        }
    }

    fun getLatestUpdates() {
        viewModelScope.launch {
            _latestUpdates.value = Resource.Loading

            _latestUpdates.value = try {
                val result = getLatestUpdatesUseCase()
                Resource.Success(result)
            } catch (e: HttpException) {
                Resource.Error(ErrorResponse(e).getMessages())
            }
        }
    }


//    fun getFeed() {
//        viewModelScope.launch {
//            delay(5000)
//            _feed.value = Resource.Loading
//
//            _feed.value = try {
//                val result = getSeasonalUseCase()
//
//                Resource.Success(result)
//            } catch (e: HttpException) {
//                Resource.Error()
//            }
//        }
//    }

    fun getStaffPicks() {
        viewModelScope.launch {
            delay(2000)
            _staffPicks.value = Resource.Loading

            _staffPicks.value = try {
                val result = getStaffPicksUseCase()

                Resource.Success(result)
            } catch (e: HttpException) {
                Resource.Error()
            }
        }
    }

    fun getRecentlyAdded() {
        viewModelScope.launch {
            delay(1000)
            _recentlyAdded.value = Resource.Loading

            _recentlyAdded.value = try {
                val result = getRecentlyAddedMangaUseCase()

                Resource.Success(result)
            } catch (e: HttpException) {
                Resource.Error()
            }
        }
    }

}

data class HomeUiState(
    val seasonal: Resource<List<HomeSeasonalMangaItem>> = Resource.Loading,
    val feed: Resource<List<HomeSeasonalMangaItem>> = Resource.Loading,
    val staffPicks: Resource<List<HomeMangaItem>> = Resource.Loading,
    val recentlyAdded: Resource<List<HomeMangaItem>> = Resource.Loading,
    val latestUpdate: Resource<List<HomeLatestUpdate>> = Resource.Loading,
)