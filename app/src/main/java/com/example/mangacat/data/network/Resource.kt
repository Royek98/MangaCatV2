package com.example.mangacat.data.network

sealed interface Resource<out T> {
    data class Success<T>(val data: T) : Resource<T>
    object Error : Resource<Nothing>
    object Loading : Resource<Nothing>
}
