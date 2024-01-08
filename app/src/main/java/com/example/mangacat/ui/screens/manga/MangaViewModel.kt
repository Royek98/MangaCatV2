package com.example.mangacat.ui.screens.manga;

import android.util.Log
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
import com.example.mangacat.domain.usecase.manga.GetRelatedMangaListCoverUseCase


import dagger.hilt.android.lifecycle.HiltViewModel;
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject


@HiltViewModel
class MangaViewModel @Inject constructor(
    private val getMangaByIdUseCase: GetMangaByIdUseCase,
    private val getChapterListByMangaIdUserCase: GetChapterListByMangaIdUserCase,
    private val getMangaCoverListUseCase: GetMangaCoverListUseCase,
    private val getRelatedMangaListCoverUseCase: GetRelatedMangaListCoverUseCase
) : ViewModel() {
    var mangaUiState: Resource<Manga> by mutableStateOf(Resource.Loading)
        private set

    var chapterListUiState: Resource<List<Chapter>> by mutableStateOf(Resource.Loading)
        private set

    private var _mangaId = ""
    private var _manga = Manga()

    private val _uploaderList: MutableSet<String> = mutableSetOf()
    var uploaderList: List<String> = listOf()
        private set

    private val _groupList: MutableSet<String> = mutableSetOf()
    var scanlationGroupList: List<String> = listOf()
        private set

    var coverList: Resource<List<Cover>> by mutableStateOf(Resource.Loading)
        private set

    var relatedMangaListCover: Resource<List<Pair<String, String>>> by mutableStateOf(Resource.Loading)
        private set

    fun getManga() {
        viewModelScope.launch {
            mangaUiState = Resource.Loading

            mangaUiState = try {
                _manga = getMangaByIdUseCase(_mangaId)
                Resource.Success(_manga)
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
                    _uploaderList.add(it.uploaderUsername)
                    if (it.scanlationGroupName != "") {
                        _groupList.add(it.scanlationGroupName)
                    }
                }
                uploaderList = _uploaderList.toList()
                scanlationGroupList = _groupList.toList()
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

    fun getRelatedMangaListCover() {
        viewModelScope.launch {
            relatedMangaListCover = Resource.Loading

            relatedMangaListCover = try {
                val test = getRelatedMangaListCoverUseCase(_manga.related.map { it!!.id })
                Log.d("TAG", "getRelatedMangaListCover: $test")
                Resource.Success(test)
            } catch (e: IOException) {
                Resource.Error
            }
        }
    }

    fun setMangaId(mangaId: String) {
        _mangaId = mangaId
    }
}