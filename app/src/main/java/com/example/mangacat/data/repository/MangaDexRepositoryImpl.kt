package com.example.mangacat.data.repository

import com.example.mangacat.data.dto.DefaultRelationships
import com.example.mangacat.data.dto.chapter.ChapterAttributes
import com.example.mangacat.data.dto.cover.CoverAttributes
import com.example.mangacat.data.dto.cutomList.CustomListAttributes
import com.example.mangacat.data.dto.libraryStatus.LibraryResponse
import com.example.mangacat.data.dto.manga.MangaAttributes
import com.example.mangacat.data.dto.manga.enums.ContentRating
import com.example.mangacat.data.dto.read.ReadResponse
import com.example.mangacat.data.dto.response.CollectionResponse
import com.example.mangacat.data.dto.response.CollectionResponseNotIncludes
import com.example.mangacat.data.dto.response.Data
import com.example.mangacat.data.dto.response.DataIncludes
import com.example.mangacat.data.dto.response.DataWithoutRelationships
import com.example.mangacat.data.dto.response.EntityResponse
import com.example.mangacat.data.network.MangaDexApiService
import com.example.mangacat.domain.repository.MangaDexRepository
import com.example.mangacat.utils.AppConstants
import retrofit2.HttpException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MangaDexRepositoryImpl @Inject constructor(
    private val service: MangaDexApiService
) : MangaDexRepository {
    override suspend fun getCustomListsMangaDexAdminUser(): CollectionResponse<CustomListAttributes> {
        return service.getUserCustomList(
            userId = "d2ae45e0-b5e2-4e7f-a688-17925c2d7d6b",
            limit = 10
        )
    }

    //toDo probably delete this one
    override suspend fun getSeasonalMangaIds():
            EntityResponse<Data<CustomListAttributes, List<DefaultRelationships>>> =
        service.getCustomListIds(AppConstants.seasonal_id)

    @Throws(HttpException::class)
    override suspend fun getMangaListByIds(
        limit: Int,
        offset: Int,
        includes: List<String>,
        contentRating: List<ContentRating>,
        ids: List<String>,
        hasAvailableChapters: Boolean,
        latestUploadedChapter: String
    ): CollectionResponse<MangaAttributes> =
        service.getMangaListByIds(
            limit,
            offset,
            includes,
            contentRating.map { it.name.lowercase() },
            ids,
            latestUploadedChapter
        )


    override suspend fun getMangaById(
        id: String,
        includes: List<String>
    ): EntityResponse<DataIncludes<MangaAttributes>> =
        service.getMangaById(mangaId = id, includes = includes)

    override suspend fun getChapterListByMangaId(
        translatedLanguage: List<String>,
        limit: Int,
        includes: List<String>,
        orderVolume: String,
        orderChapter: String,
        offset: Int,
        contentRating: List<String>,
        mangaId: String
    ): CollectionResponse<ChapterAttributes> =
        service.getChapterListByMangaId(
            mangaId,
            translatedLanguage,
            limit,
            includes,
            orderVolume,
            orderChapter,
            offset,
            contentRating,
        )

    override suspend fun getReadPages(chapterId: String): ReadResponse = service.getReadPages(chapterId)

    override suspend fun getMangaCoverList(mangaId: String):
            CollectionResponseNotIncludes<DataWithoutRelationships<CoverAttributes>> =
        service.getMangaCoverList(orderVolume = "asc", limit = 10, offset = 0, mangaId = mangaId)

    override suspend fun getRecentlyAddedManga():
            CollectionResponse<MangaAttributes> = service.getRecentlyAddedManga()

    override suspend fun getLibraryStatus(accessToken: String): LibraryResponse =
        service.getLibraryStatuses(accessToken)

    override suspend fun getLatestUpdates(): CollectionResponse<ChapterAttributes> =
        service.getLatestUpdates(limit = 5)

    override suspend fun getCoverListByMangaIds(
        limit: Int,
        offset: Int,
        mangaIds: List<String>
    ): CollectionResponse<CoverAttributes> = service.getCoverListByMangaIds(limit, offset, mangaIds)


}