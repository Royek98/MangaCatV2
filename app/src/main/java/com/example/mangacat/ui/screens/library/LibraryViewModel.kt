package com.example.mangacat.ui.screens.library

import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mangacat.data.network.Resource
import com.example.mangacat.domain.model.Manga
import com.example.mangacat.domain.usecase.library.GetLibraryStatusesUseCase
import com.example.mangacat.ui.screens.home.HomeUiState
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
    private val getLibraryStatusesUseCase: GetLibraryStatusesUseCase
) : ViewModel() {
    private val _statusList =
        MutableStateFlow<Resource<LinkedHashMap<String, List<String>>>>(Resource.Loading)

    private val _reading =
        MutableStateFlow<Resource<List<Manga>>>(Resource.Loading)

    private val _currentTab =
        MutableStateFlow(0)

    val libraryUiState: StateFlow<LibraryUiState> =
        combine(
            _statusList,
            _reading,
            _currentTab
        ) {
            statusList, reading, currentTab ->
            LibraryUiState(
                currentTab = currentTab,
                statusList = statusList,
                reading = reading
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

    fun changeTab(index: Int) {
        _currentTab.value = index
    }
}

data class LibraryUiState(
    val currentTab: Int = 0,
    val statusList: Resource<Map<String, List<String>>> = Resource.Loading,
    val reading: Resource<List<Manga>> = Resource.Loading
)