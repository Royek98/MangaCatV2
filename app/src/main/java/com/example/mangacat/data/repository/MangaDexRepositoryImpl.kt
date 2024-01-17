package com.example.mangacat.data.repository

import com.example.mangacat.data.dto.DefaultRelationships
import com.example.mangacat.data.dto.authentication.AuthResponse
import com.example.mangacat.data.dto.chapter.ChapterAttributes
import com.example.mangacat.data.dto.cover.CoverAttributes
import com.example.mangacat.data.dto.cutomList.CustomListAttributes
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
import com.example.mangacat.data.network.Resource
import com.example.mangacat.domain.repository.MangaDexRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MangaDexRepositoryImpl @Inject constructor(
    private val service: MangaDexApiService
) : MangaDexRepository {
    override suspend fun getSeasonalMangaIds(): EntityResponse<Data<CustomListAttributes, List<DefaultRelationships>>> = service.getSeasonalMangaIds()
    override suspend fun getMangaListByIds(
        limit: Int,
        offset: Int,
        includes: List<String>,
        contentRating: List<ContentRating>,
        ids: List<String>
    ): CollectionResponse<MangaAttributes> {
        return service.getMangaListByIds(limit, offset, includes, contentRating, ids)
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

}