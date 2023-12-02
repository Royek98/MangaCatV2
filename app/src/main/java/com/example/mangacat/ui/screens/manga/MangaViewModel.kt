package com.example.mangacat.ui.screens.manga;

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mangacat.data.network.Resource
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

    private var _mangaId = ""

    fun getManga() {
        viewModelScope.launch {
            mangaUiState = Resource.Loading

            mangaUiState = try {
                val result = getMangaByIdUseCase(_mangaId)

                Resource.Success(result)
            } catch (e: IOException) {
                Resource.Error
            }
        }
    }

    fun getChapterList() {
        viewModelScope.launch {
            val result = getChapterListByMangaIdUserCase(_mangaId)
            Log.d("TAG", "getChapterList: $result")
        }
    }

    fun setMangaId(mangaId: String) {
        _mangaId = mangaId
    }
}