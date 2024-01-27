package com.example.mangacat.data.network

import com.example.mangacat.data.dto.DefaultRelationships
import com.example.mangacat.data.dto.chapter.ChapterAttributes
import com.example.mangacat.data.dto.cover.CoverAttributes
import com.example.mangacat.data.dto.cutomList.CustomListAttributes
import com.example.mangacat.data.dto.manga.MangaAttributes
import com.example.mangacat.data.dto.response.CollectionResponse
import com.example.mangacat.data.dto.response.Data
import com.example.mangacat.data.dto.response.EntityResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface MangaDexApiService {
//    @GET("list/${AppConstants.seasonal_id}")
//    suspend fun getSeasonalManga(): Response<Attributes, Relationships>

    @GET("list")
    suspend fun getCustomListIds(@Path("id") customListId: String):
            EntityResponse<Data<CustomListAttributes, List<DefaultRelationships>>>

    @GET("/user/{id}/list")
    suspend fun getUserCustomList(
        @Path("id") userId: String,
        @Query("limit") limit: Int
    ) : CollectionResponse<CustomListAttributes>

    // https://api.mangadex.org/manga?limit=32&offset=32&includes[]=cover_art&includes[]=artist&includes[]=author&contentRating[]=safe&contentRating[]=suggestive&contentRating[]=erotica&ids[]=98ca8bd8-c834-4627-ac64-4b765be22464&ids[]
    @GET("manga")
    @JvmSuppressWildcards
    suspend fun getMangaListByIds(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
        @Query("includes[]") includes: List<String>,
        @Query("contentRating[]") contentRating: List<String>,
        @Query("ids[]") ids: List<String>,
        @Query("order[latestUploadedChapter]") latestUploadedChapter: String = "desc",
    ): CollectionResponse<MangaAttributes>

    @GET("manga?limit=15&contentRating[]=safe&contentRating[]=suggestive&contentRating[]=" +
            "erotica&order[createdAt]=desc&includes[]=cover_art" +
            "&hasAvailableChapters=true&availableTranslatedLanguage[]=en")
    suspend fun getRecentlyAddedManga(
    ): CollectionResponse<MangaAttributes>

    @GET("chapter?translatedLanguage[]=en&includes[]=scanlation_group&includes[]=manga&includes[]=user" +
            "&contentRating[]=safe&contentRating[]=suggestive" +
            "&contentRating[]=erotica&order[readableAt]=desc")
    suspend fun getLatestUpdates(
        @Query("limit") limit: Int,
    ): CollectionResponse<ChapterAttributes>


    @GET("cover")
    suspend fun getCoverListByMangaIds(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
        @Query("manga[]") mangaIds: List<String>,
    ): CollectionResponse<CoverAttributes>
}