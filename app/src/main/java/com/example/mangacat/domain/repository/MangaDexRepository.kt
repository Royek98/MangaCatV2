package com.example.mangacat.domain.repository

import com.example.mangacat.data.dto.DefaultRelationships
import com.example.mangacat.data.dto.ScanlationGroupIncludes
import com.example.mangacat.data.dto.authentication.AuthResponse
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
import com.example.mangacat.data.dto.user.UserAttributes
import com.example.mangacat.data.network.Resource

interface MangaDexRepository {

    suspend fun getListOfSeasonalCustomLists(): CollectionResponse<CustomListAttributes>

    suspend fun getSeasonalMangaIds(): EntityResponse<Data<CustomListAttributes, List<DefaultRelationships>>>
    suspend fun getMangaListByIds(
        limit: Int,
        offset: Int,
        includes: List<String>,
        contentRating: List<ContentRating>,
        ids: List<String>
    ): CollectionResponse<MangaAttributes>

    suspend fun getMangaById(id: String): EntityResponse<DataIncludes<MangaAttributes>>

    suspend fun getChapterList(mangaId: String): CollectionResponse<ChapterAttributes>

    suspend fun getReadPages(chapterId: String): Read

    suspend fun getMangaCoverList(mangaId: String): CollectionResponseNotIncludes<DataWithoutRelationships<CoverAttributes>>

    suspend fun getStuffPicks(): EntityResponse<Data<CustomListAttributes, List<DefaultRelationships>>>

    suspend fun getLibraryStatus(): LibraryResponse
}