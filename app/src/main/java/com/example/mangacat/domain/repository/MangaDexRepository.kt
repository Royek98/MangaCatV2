package com.example.mangacat.domain.repository

import com.example.mangacat.data.dto.DefaultRelationships
import com.example.mangacat.data.dto.chapter.ChapterAttributes
import com.example.mangacat.data.dto.cover.CoverAttributes
import com.example.mangacat.data.dto.cutomList.CustomListAttributes
import com.example.mangacat.data.dto.libraryStatus.LibraryResponse
import com.example.mangacat.data.dto.manga.MangaAttributes
import com.example.mangacat.data.dto.manga.enums.ContentRating
import com.example.mangacat.data.dto.read.Read
import com.example.mangacat.data.dto.response.CollectionResponse
import com.example.mangacat.data.dto.response.CollectionResponseNotIncludes
import com.example.mangacat.data.dto.response.Data
import com.example.mangacat.data.dto.response.DataIncludes
import com.example.mangacat.data.dto.response.DataWithoutRelationships
import com.example.mangacat.data.dto.response.EntityResponse
import com.example.mangacat.data.dto.response.enums.Type
import retrofit2.http.Query

interface MangaDexRepository {

    suspend fun getCustomListsMangaDexAdminUser(): CollectionResponse<CustomListAttributes>

    suspend fun getSeasonalMangaIds(): EntityResponse<Data<CustomListAttributes, List<DefaultRelationships>>>
    suspend fun getMangaListByIds(
        limit: Int,
        offset: Int = 0,
        includes: List<String>,
        contentRating: List<ContentRating>,
        ids: List<String>,
        hasAvailableChapters: Boolean = true,
        latestUploadedChapter: String = "desc"
    ): CollectionResponse<MangaAttributes>

    suspend fun getMangaById(id: String, includes: List<String>): EntityResponse<DataIncludes<MangaAttributes>>

    suspend fun getChapterListByMangaId(
        translatedLanguage: List<String> = listOf("en"),
        limit: Int = 10,
        includes: List<String> = listOf(
            Type.SCANLATION_GROUP.name.lowercase(),
            Type.USER.name.lowercase()
        ),
        orderVolume: String = "desc",
        orderChapter: String = "desc",
        offset: Int = 0,
        contentRating: List<String> = listOf(
            ContentRating.SAFE.name.lowercase(),
            ContentRating.SUGGESTIVE.name.lowercase(),
            ContentRating.EROTICA.name.lowercase(),
            ContentRating.PORNOGRAPHIC.name.lowercase()
        ),
        mangaId: String
    ): CollectionResponse<ChapterAttributes>

    suspend fun getReadPages(chapterId: String): Read

    suspend fun getMangaCoverList(mangaId: String): CollectionResponseNotIncludes<DataWithoutRelationships<CoverAttributes>>

    suspend fun getRecentlyAddedManga(): CollectionResponse<MangaAttributes>

    suspend fun getLibraryStatus(): LibraryResponse

    suspend fun getLatestUpdates(): CollectionResponse<ChapterAttributes>

    suspend fun getCoverListByMangaIds(
        limit: Int = 5,
        offset: Int = 0,
        mangaIds: List<String>,
    ): CollectionResponse<CoverAttributes>
}