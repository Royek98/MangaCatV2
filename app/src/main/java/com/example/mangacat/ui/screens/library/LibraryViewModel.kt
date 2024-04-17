package com.example.mangacat.ui.screens.library

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mangacat.data.dto.English
import com.example.mangacat.data.dto.manga.MangaAttributes
import com.example.mangacat.data.dto.manga.enums.ContentRating
import com.example.mangacat.data.dto.manga.enums.Status
import com.example.mangacat.data.dto.response.DataIncludes
import com.example.mangacat.data.dto.response.DataWithoutRelationships
import com.example.mangacat.data.dto.response.enums.Type
import com.example.mangacat.data.network.Resource
import com.example.mangacat.domain.model.Manga
import com.example.mangacat.domain.usecase.authentication.GetTokenUseCase
import com.example.mangacat.domain.usecase.library.GetLibraryStatusesUseCase
import com.example.mangacat.domain.usecase.library.GetMangaListByIds
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class LibraryViewModel @Inject constructor(
    private val getLibraryStatusesUseCase: GetLibraryStatusesUseCase,
    private val getMangaListByIds: GetMangaListByIds,
    private val getTokenUseCase: GetTokenUseCase
) : ViewModel() {
    private val _statusList =
        MutableStateFlow<Resource<LinkedHashMap<String, List<String>>>>(Resource.Loading)

    private val _mangaList =
        MutableStateFlow<LinkedHashMap<String, MutableList<Resource<List<Manga>>>>>(linkedMapOf())

//    private val _reading =
//        MutableStateFlow<List<Resource<List<Manga>>>>(listOf(Resource.Loading))
//
//    private val _completed =
//        MutableStateFlow<Resource<List<Manga>>>(Resource.Loading)

    private val _currentTab =
        MutableStateFlow(0)

    val libraryUiState: StateFlow<LibraryUiState> =
        combine(
            _statusList,
            _mangaList,
//            _reading,
//            _completed,
            _currentTab
        ) {
            statusList,
            mangaList,
//            reading,
//            completed,
            currentTab ->
            LibraryUiState(
                currentTab = currentTab,
                statusList = statusList,
                mangaList = mangaList as LinkedHashMap<String, List<Resource<List<Manga>>>>
//                reading = reading,
//                completed = completed
            )
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            initialValue = LibraryUiState()
        )

    init {
        getStatuses()
    }

    private fun getStatuses() {
        viewModelScope.launch {
            _statusList.value = Resource.Loading
            _statusList.value = try {
                val token = getTokenUseCase()
                Log.d("STATUSES", "request was send")
                val response = getLibraryStatusesUseCase("Bearer ${token?.accessToken!!}")

                val mangaList = mutableMapOf<String, MutableList<Resource<List<Manga>>>>()
                response.keys.forEach { key ->
                    mangaList[key] = mutableListOf(Resource.Loading)
                }
                _mangaList.value = mangaList as LinkedHashMap<String, MutableList<Resource<List<Manga>>>>
                Resource.Success(response)
            } catch (e: IOException) {
                Resource.Error()
            } catch (e: NullPointerException) {
                Resource.Error()
            }
        }
    }

    fun getMangaList(key: String, mangaIds: List<String>, offset: Int = 0) {
        val currentList = _mangaList.value
        viewModelScope.launch {
            _mangaList.value = try {
                Log.d("getMangaList", "request was send with key $key")
                if (offset > 0) {
//                    _mangaList.value[key]?.add(Resource.Loading)
                    currentList[key]?.add(Resource.Loading)
                }

                val response = getMangaListByIds(mangaIds)

                if (currentList[key]?.size == 1) {
                    currentList[key]?.removeAt(0);
                } else {
                    currentList[key]?.removeAt(currentList.size-1)
                }

                currentList[key]?.add(Resource.Success(response))
                currentList
//
//                if (_mangaList.value[key]?.size == 1) {
//                    _mangaList.value[key]?.removeAt(0) // remove loading state
//                } else {
//                    _mangaList.value[key]?.removeAt(_mangaList.value[key]!!.size-1) // remove loading state
//                }
//
//                _mangaList.value[key]?.add(Resource.Success(response))

//                _mangaList.value[key]?.filter { manga -> manga != Resource.Loading }.let {
//                    if (it != null) {
//                        _mangaList.value[key] = it.toMutableList()
//                    }
//                }
            } catch (e: IOException) {
                _mangaList.value[key]?.removeAt(_mangaList.value[key]!!.size-1) // remove loading state
                _mangaList.value[key]?.add(Resource.Error())
                currentList
            }
        }
    }

    fun changeTab(index: Int) {
        _currentTab.value = index
    }
}

data class LibraryUiState(
    val currentTab: Int = 0,
    val statusList: Resource<Map<String, List<String>>> = Resource.Loading,
//    val reading: List<Resource<List<Manga>>> = listOf(Resource.Loading),
//    val completed: Resource<List<Manga>> = Resource.Loading
    val mangaList: LinkedHashMap<String, List<Resource<List<Manga>>>> = linkedMapOf()
)