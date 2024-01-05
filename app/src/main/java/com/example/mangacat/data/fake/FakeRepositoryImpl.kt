package com.example.mangacat.data.fake

import android.content.Context
import com.example.mangacat.data.dto.DefaultRelationships
import com.example.mangacat.data.dto.Includes
import com.example.mangacat.data.dto.IncludesPolymorphicSerializer
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
import com.example.mangacat.domain.repository.MangaDexRepository
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import javax.inject.Singleton


@Singleton
class FakeRepositoryImpl(
    private val context: Context
) : MangaDexRepository {

    private val module = SerializersModule {
        polymorphicDefaultDeserializer(Includes::class) { IncludesPolymorphicSerializer }
    }

    private val json = Json {
        ignoreUnknownKeys = true
        serializersModule = module
    }

    override suspend fun getSeasonalMangaIds(): EntityResponse<Data<CustomListAttributes, List<DefaultRelationships>>> =
        json.decodeFromString(readJSONFromAssets(context, "SeasonalIdList.json"))


    override suspend fun getMangaListByIds(
        limit: Int,
        offset: Int,
        includes: List<String>,
        contentRating: List<ContentRating>,
        ids: List<String>
    ): CollectionResponse<MangaAttributes> =
        json.decodeFromString(readJSONFromAssets(context, "SeasonalResponse_Limit10_Offset0.json"))

    override suspend fun getMangaById(id: String): EntityResponse<DataIncludes<MangaAttributes>> =
        json.decodeFromString(readJSONFromAssets(context, "$id.json"))

    override suspend fun getChapterList(mangaId: String): CollectionResponse<ChapterAttributes> =
        json.decodeFromString(readJSONFromAssets(context, "feed$mangaId.json"))

    override suspend fun getReadPages(chapterId: String): Read =
        json.decodeFromString(readJSONFromAssets(context, "59chapter-$chapterId.json"))
    override suspend fun getMangaCoverList(mangaId: String): CollectionResponseNotIncludes<DataWithoutRelationships<CoverAttributes>> =
        json.decodeFromString(readJSONFromAssets(context, "gluttonyCoverList.json"))

    private fun readJSONFromAssets(context: Context, path: String): String =
         context.assets.open(path).readBytes().toString(Charsets.UTF_8)
}