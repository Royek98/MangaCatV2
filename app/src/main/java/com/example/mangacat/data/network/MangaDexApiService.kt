package com.example.mangacat.data.network

import com.example.mangacat.data.dto.DefaultRelationships
import com.example.mangacat.data.dto.cutomList.CustomListAttributes
import com.example.mangacat.data.dto.manga.MangaAttributes
import com.example.mangacat.data.dto.manga.enums.ContentRating
import com.example.mangacat.data.dto.response.CollectionResponse
import com.example.mangacat.data.dto.response.EntityResponse
import com.example.mangacat.utils.AppConstants
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface MangaDexApiService {
//    @GET("list/${AppConstants.seasonal_id}")
//    suspend fun getSeasonalManga(): Response<Attributes, Relationships>

    @GET("list/${AppConstants.seasonal_id}")
    suspend fun getSeasonalMangaIds(): EntityResponse<CustomListAttributes, List<DefaultRelationships>>


    // https://api.mangadex.org/manga?limit=32&offset=32&includes[]=cover_art&includes[]=artist&includes[]=author&contentRating[]=safe&contentRating[]=suggestive&contentRating[]=erotica&ids[]=98ca8bd8-c834-4627-ac64-4b765be22464&ids[]
    @GET("manga")
    @JvmSuppressWildcards
    suspend fun getMangaListByIds(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
        @Query("includes[]") includes: List<String>,
        @Query("contentRating[]") contentRating: List<ContentRating>,
        @Query("ids[]") ids: List<String>,
        @Query("order[latestUploadedChapter]") latestUploadedChapter: String = "desc"
    ): CollectionResponse<MangaAttributes>
}