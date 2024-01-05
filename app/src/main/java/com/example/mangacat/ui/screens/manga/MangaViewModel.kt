package com.example.mangacat.ui.screens.manga;

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mangacat.data.network.Resource
import com.example.mangacat.domain.model.Chapter
import com.example.mangacat.domain.model.Cover
import com.example.mangacat.domain.model.Manga
import com.example.mangacat.domain.usecase.manga.GetChapterListByMangaIdUserCase
import com.example.mangacat.domain.usecase.manga.GetMangaByIdUseCase
import com.example.mangacat.domain.usecase.manga.GetMangaCoverListUseCase


import dagger.hilt.android.lifecycle.HiltViewModel;
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject


@HiltViewModel
class MangaViewModel @Inject constructor(
    private val getMangaByIdUseCase: GetMangaByIdUseCase,
    private val getChapterListByMangaIdUserCase: GetChapterListByMangaIdUserCase,
    private val getMangaCoverListUseCase: GetMangaCoverListUseCase
) : ViewModel() {
    var mangaUiState: Resource<Manga> by mutableStateOf(Resource.Loading)
        private set

    var chapterListUiState: Resource<List<Chapter>> by mutableStateOf(Resource.Loading)
        private set

    private var _mangaId = ""

    private val uploaderListPrivate: MutableSet<String> = mutableSetOf()
    var uploaderList: List<String> = listOf()
        private set

    private val groupListPrivate: MutableSet<String> = mutableSetOf()
    var scanlationGroupList: List<String> = listOf()
        private set

    var coverList: Resource<List<Cover>> by mutableStateOf(Resource.Loading)
        private set

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
                val chapterList = getChapterListByMangaIdUserCase(_mangaId)
                chapterList.forEach {
                    uploaderListPrivate.add(it.uploaderUsername)
                    if (it.scanlationGroupName != "") {
                        groupListPrivate.add(it.scanlationGroupName)
                    }
                }
                uploaderList = uploaderListPrivate.toList()
                scanlationGroupList = groupListPrivate.toList()
                Resource.Success(chapterList)
            } catch (e: IOException) {
                Resource.Error
            }
        }
    }

    fun getCoverList() {
        viewModelScope.launch {
            coverList = Resource.Loading

            coverList = try {
                Resource.Success(getMangaCoverListUseCase(_mangaId))
            } catch (e: IOException) {
                Resource.Error
            }
        }
    }

    fun setMangaId(mangaId: String) {
        _mangaId = mangaId
    }
}