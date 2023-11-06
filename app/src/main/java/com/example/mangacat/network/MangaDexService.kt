package com.example.mangacat.network

import com.example.mangacat.model.mangaList.MangaList
import com.example.mangacat.utils.AppConstants
import retrofit2.http.GET
import javax.inject.Singleton

@Singleton
interface MangaDexService {
    @GET("list/${AppConstants.seasonal_id}")
    suspend fun getSeasonalManga(): MangaList
}