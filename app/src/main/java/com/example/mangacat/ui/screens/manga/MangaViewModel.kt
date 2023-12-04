package com.example.mangacat.ui.screens.manga;

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mangacat.data.network.Resource
import com.example.mangacat.domain.model.Chapter
import com.example.mangacat.domain.model.Manga
import com.example.mangacat.domain.usecase.manga.GetChapterListByMangaIdUserCase
import com.example.mangacat.domain.usecase.manga.GetMangaByIdUseCase


import dagger.hilt.android.lifecycle.HiltViewModel;
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject


@HiltViewModel
class MangaViewModel @Inject constructor(
    private val getMangaByIdUseCase: GetMangaByIdUseCase,
    private val getChapterListByMangaIdUserCase: GetChapterListByMangaIdUserCase
) : ViewModel() {
    var mangaUiState: Resource<Manga> by mutableStateOf(Resource.Loading)
        private set

    var chapterListUiState: Resource<List<Chapter>> by mutableStateOf(Resource.Loading)
        private set

    private var _mangaId = ""

    fun getManga() {
        viewModelScope.launch {
            mangaUiState = Resource.Loading

            mangaUiState = try {
                Resource.Success(getMangaByIdUseCase(_mangaId))
            } catch (e: IOException) {
                Resource.Error
            }
        }
    }

    fun getChapterList() {
        viewModelScope.launch {
            chapterListUiState = Resource.Loading

            chapterListUiState = try {
                Resource.Success(getChapterListByMangaIdUserCase(_mangaId))
            } catch (e: IOException) {
                Resource.Error
            }
        }
    }

    fun setMangaId(mangaId: String) {
        _mangaId = mangaId
    }
}