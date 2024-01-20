package com.example.mangacat.ui.screens.feed

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Image
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mangacat.data.network.Resource
import com.example.mangacat.domain.model.ChapterFeedItem
import com.example.mangacat.domain.model.MangaFeedItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject constructor(

)  : ViewModel() {

    private val _uiState = MutableStateFlow<Resource<List<MangaFeedItem>>>(Resource.Loading)
    val uiState: StateFlow<Resource<List<MangaFeedItem>>> = _uiState

    init {
        getUiState()
    }

    private fun getUiState() {
        viewModelScope.launch {
            delay(1000)
            _uiState.value = Resource.Loading

            _uiState.value = try {
                val result = listOf(
                    MangaFeedItem(
                        mangaId = "",
                        cover = Icons.Default.Image,
                        titleManga = "Title",
                        chapterList = listOf(
                            ChapterFeedItem(
                                chapterId = "",
                                chapterTitle = "Chapter Title",
                                userNameUploader = "Username",
                                groupNameUploader = "Groupname",
                                timestamp = "2023-11-23",
                                hasSeen = false
                            )
                        )
                    ),
                    MangaFeedItem(
                        mangaId = "",
                        cover = Icons.Default.Image,
                        titleManga = "Title2",
                        chapterList = listOf(
                            ChapterFeedItem(
                                chapterId = "",
                                chapterTitle = "Chapter Title2",
                                userNameUploader = "Username2",
                                groupNameUploader = "Groupname2",
                                timestamp = "2023-11-24",
                                hasSeen = false
                            ),
                            ChapterFeedItem(
                                chapterId = "",
                                chapterTitle = "Chapter Title3",
                                userNameUploader = "Username2",
                                groupNameUploader = "Groupname2",
                                timestamp = "2023-11-24",
                                hasSeen = true
                            )
                        )
                    )
                )

                Resource.Success(result)
            } catch (e: IOException) {
                Resource.Error()
            }
        }
    }

}