package com.example.mangacat.ui.screens.read

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mangacat.data.network.Resource
import com.example.mangacat.domain.model.Read
import com.example.mangacat.domain.usecase.read.GetReadPagesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class ReadViewModel @Inject constructor(
    private val getReadPagesUseCase: GetReadPagesUseCase
) : ViewModel() {
    var readUiState: Resource<Read> by mutableStateOf(Resource.Loading)
        private set

    var showBar by mutableStateOf(false)
        private set

    private var _chapterId = ""

    fun getReadPages() {
        viewModelScope.launch {
            readUiState = Resource.Loading

            readUiState = try {
                Resource.Success(getReadPagesUseCase(_chapterId))
            } catch (e: IOException) {
                Resource.Error
            }
        }
    }

    fun setChapterId(chapterId: String) {
        _chapterId = chapterId
    }

    fun showBarChangeState() {
        showBar = !showBar
        Log.d("TAG", "showBarChangeState: $showBar")
    }
}