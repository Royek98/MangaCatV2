package com.example.mangacat.data.network

import com.example.mangacat.data.dto.response.Response
import com.example.mangacat.data.dto.cutomList.CustomListAttributes
import com.example.mangacat.data.dto.manga.MangaAttributes
import com.example.mangacat.data.dto.Relationships
import retrofit2.http.GET
import javax.inject.Singleton

@Singleton
interface MangaDexApiService {
//    @GET("list/${AppConstants.seasonal_id}")
//    suspend fun getSeasonalManga(): Response<Attributes, Relationships>

    suspend fun getSeasonalMangaIds(): Response<CustomListAttributes, Relationships>


    @GET("manga/67a7dfd1-a39d-46c8-8ff2-9a24a9abaeb6")
    suspend fun getMangaById(): Response<MangaAttributes, Relationships>
}