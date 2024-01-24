package com.example.mangacat.data.network

sealed interface Resource<out T> {
    data class Success<T>(val data: T) : Resource<T>
    data class Error(val message: List<String>? = listOf()) : Resource<Nothing>
    object Loading : Resource<Nothing>
}
