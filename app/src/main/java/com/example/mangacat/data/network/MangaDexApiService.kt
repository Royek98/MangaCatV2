package com.example.mangacat.data.network

import com.example.mangacat.data.dto.DefaultRelationships
import com.example.mangacat.data.dto.ScanlationGroupIncludes
import com.example.mangacat.data.dto.authentication.AuthRequest
import com.example.mangacat.data.dto.authentication.AuthResponse
import com.example.mangacat.data.dto.authentication.IsAuthenticatedResponse
import com.example.mangacat.data.dto.chapter.ChapterAttributes
import com.example.mangacat.data.dto.cover.CoverAttributes
import com.example.mangacat.data.dto.cutomList.CustomListAttributes
import com.example.mangacat.data.dto.libraryStatus.LibraryResponse
import com.example.mangacat.data.dto.manga.MangaAttributes
import com.example.mangacat.data.dto.read.ReadResponse
import com.example.mangacat.data.dto.response.CollectionResponse
import com.example.mangacat.data.dto.response.CollectionResponseNotIncludes
import com.example.mangacat.data.dto.response.Data
import com.example.mangacat.data.dto.response.DataIncludes
import com.example.mangacat.data.dto.response.DataWithoutRelationships
import com.example.mangacat.data.dto.response.EntityResponse
import com.example.mangacat.data.dto.user.UserAttributes
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface MangaDexApiService {
    @GET("manga/{id}")
    suspend fun getMangaById(
        @Path("id") mangaId: String,
        @Query("includes[]") includes: List<String>
    ): EntityResponse<DataIncludes<MangaAttributes>>


    @GET("manga/{id}/feed")
    suspend fun getChapterListByMangaId(
        @Path("id") mangaId: String,
        @Query("translatedLanguage[]") translatedLanguage: List<String>,
        @Query("limit") limit: Int,
        @Query("includes[]") includes: List<String>,
        @Query("order[volume]") orderVolume: String,
        @Query("order[chapter]") orderChapter: String,
        @Query("offset") offset: Int,
        @Query("contentRating[]") contentRating: List<String>
    ): CollectionResponse<ChapterAttributes>

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
        @Query("order[latestUploadedChapter]") latestUploadedChapter: String,
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

    @GET("cover")
    suspend fun getMangaCoverList(
        @Query("order[volume]") orderVolume: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
        @Query("manga[]") mangaId: String,
    ): CollectionResponseNotIncludes<DataWithoutRelationships<CoverAttributes>>

    @GET("at-home/server/{id}?forcePort443=false")
    suspend fun getReadPages(
        @Path("id") chapterId: String
    ): ReadResponse

    @GET("/user/me?includes[]=scanlation_group")
    suspend fun getAuthenticatedUserInformation(
        @Header("Authorization") bearerToken: String
    ): EntityResponse<Data<UserAttributes, List<ScanlationGroupIncludes>>>

    @GET("auth/check")
    suspend fun check(
        @Header("Authorization") bearerToken: String
    ): IsAuthenticatedResponse

    @GET("manga/status")
    suspend fun getLibraryStatuses(
        @Header("Authorization") bearerToken: String
    ): LibraryResponse
}