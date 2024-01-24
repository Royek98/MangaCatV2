package com.example.mangacat.data.repository

import android.util.Log
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
    override suspend fun getListOfSeasonalCustomLists(): CollectionResponse<CustomListAttributes> {
        return service.getUserCustomList(
            userId = "d2ae45e0-b5e2-4e7f-a688-17925c2d7d6b",
            limit = 10
        )
    }

    //toDo probably delete this one
    override suspend fun getSeasonalMangaIds():
            EntityResponse<Data<CustomListAttributes, List<DefaultRelationships>>> =
        service.getCustomListIds(AppConstants.seasonal_id)

    override suspend fun getMangaListByIds(
        limit: Int,
        offset: Int,
        includes: List<String>,
        contentRating: List<ContentRating>,
        ids: List<String>
    ): CollectionResponse<MangaAttributes> {
        val response = service.getMangaListByIds(
            limit,
            offset,
            includes,
//            contentRating.map { it.name.lowercase() },
            contentRating.map { it.name },
            ids
        )

        return if (response.isSuccessful) {
            response.body()!!
        } else {
            throw HttpException(response)
        }
    }

    override suspend fun getMangaById(id: String): EntityResponse<DataIncludes<MangaAttributes>> {
        TODO("Not yet implemented")
    }

    override suspend fun getChapterList(mangaId: String): CollectionResponse<ChapterAttributes> {
        TODO("Not yet implemented")
    }

    override suspend fun getReadPages(chapterId: String): Read {
        TODO("Not yet implemented")
    }

    override suspend fun getMangaCoverList(mangaId: String): CollectionResponseNotIncludes<DataWithoutRelationships<CoverAttributes>> {
        TODO("Not yet implemented")
    }

    override suspend fun getStuffPicks(): EntityResponse<Data<CustomListAttributes, List<DefaultRelationships>>> {
        TODO("Not yet implemented")
    }

    override suspend fun getLibraryStatus(): LibraryResponse {
        TODO("Not yet implemented")
    }

}