package com.example.mangacat.data.fake

import android.content.Context
import com.example.mangacat.data.dto.DefaultRelationships
import com.example.mangacat.data.dto.Includes
import com.example.mangacat.data.dto.IncludesPolymorphicSerializer
import com.example.mangacat.data.dto.cutomList.CustomListAttributes
import com.example.mangacat.data.dto.manga.MangaAttributes
import com.example.mangacat.data.dto.manga.enums.ContentRating
import com.example.mangacat.data.dto.response.CollectionResponse
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

    override suspend fun getSeasonalMangaIds(): EntityResponse<CustomListAttributes, List<DefaultRelationships>> =
        json.decodeFromString(ReadJSONFromAssets(context, "SeasonalIdList.json"))


    override suspend fun getMangaListByIds(
        limit: Int,
        offset: Int,
        includes: List<String>,
        contentRating: List<ContentRating>,
        ids: List<String>
    ): CollectionResponse<MangaAttributes> =
        json.decodeFromString(ReadJSONFromAssets(context, "SeasonalResponse_Limit10_Offset0.json"))



    fun ReadJSONFromAssets(context: Context, path: String): String  =
         context.assets.open(path).readBytes().toString(Charsets.UTF_8)
}