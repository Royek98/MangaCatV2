package com.example.mangacat.network

import com.example.mangacat.model.response.Response
import com.example.mangacat.model.cutomList.CustomListAttributes
import com.example.mangacat.model.manga.MangaAttributes
import com.example.mangacat.model.Relationships
import retrofit2.http.GET
import javax.inject.Singleton

@Singleton
interface MangaDexService {
//    @GET("list/${AppConstants.seasonal_id}")
//    suspend fun getSeasonalManga(): Response<Attributes, Relationships>

    suspend fun getSeasonalMangaIds(): Response<CustomListAttributes, Relationships>


    @GET("manga/67a7dfd1-a39d-46c8-8ff2-9a24a9abaeb6")
    suspend fun getMangaById(): Response<MangaAttributes, Relationships>
}