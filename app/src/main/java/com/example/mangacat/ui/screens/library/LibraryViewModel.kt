package com.example.mangacat.ui.screens.library

import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mangacat.data.network.Resource
import com.example.mangacat.domain.model.Manga
import com.example.mangacat.domain.usecase.library.GetLibraryStatusesUseCase
import com.example.mangacat.domain.usecase.library.GetMangaListByIds
import com.example.mangacat.ui.screens.home.HomeUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class LibraryViewModel @Inject constructor(
    private val getLibraryStatusesUseCase: GetLibraryStatusesUseCase,
    private val getMangaListByIds: GetMangaListByIds
) : ViewModel() {
    private val _statusList =
        MutableStateFlow<Resource<LinkedHashMap<String, List<String>>>>(Resource.Loading)

    private val _reading =
        MutableStateFlow<List<Resource<List<Manga>>>>(listOf(Resource.Loading))

    private val _completed =
        MutableStateFlow<Resource<List<Manga>>>(Resource.Loading)

    private val _currentTab =
        MutableStateFlow(0)

    val libraryUiState: StateFlow<LibraryUiState> =
        combine(
            _statusList,
            _reading,
            _completed,
            _currentTab
        ) {
            statusList, reading, completed, currentTab ->
            LibraryUiState(
                currentTab = currentTab,
                statusList = statusList,
                reading = reading,
                completed = completed
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
                val response = getLibraryStatusesUseCase()
                Resource.Success(response)
            } catch (e: IOException) {
                Resource.Error()
            }
        }
    }

    fun getReading(mangaIds: List<String>, offset: Int = 0) {
        viewModelScope.launch {
            val currentList = _reading.value.toMutableList()
            try {
                // Update the state to Loading
                if (offset != 0) {
                    currentList.add(Resource.Loading)
                }

                val response = getMangaListByIds(mangaIds)
                currentList.removeAt(currentList.size-1) // remove loading state
                currentList.add(Resource.Success(response))
                _reading.value = currentList
            } catch (e: IOException) {
                currentList.removeAt(currentList.size-1) // remove loading state
                currentList.add(Resource.Error())
                _reading.value = currentList
            }
        }
    }

    fun getCompleted(mangaIds: List<String>) {
        viewModelScope.launch {
            delay(2000)
            _completed.value = try {
                val response = getMangaListByIds(mangaIds)
                Resource.Success(response)
            } catch (e: IOException) {
                Resource.Error()
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
    val reading: List<Resource<List<Manga>>> = listOf(Resource.Loading),
    val completed: Resource<List<Manga>> = Resource.Loading
)