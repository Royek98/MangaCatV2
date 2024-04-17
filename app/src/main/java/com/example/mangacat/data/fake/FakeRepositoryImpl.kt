package com.example.mangacat.data.fake

import android.content.Context
import com.example.mangacat.data.dto.DefaultRelationships
import com.example.mangacat.data.dto.Includes
import com.example.mangacat.data.dto.IncludesPolymorphicSerializer
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
import com.example.mangacat.data.dto.response.enums.Result
import com.example.mangacat.data.dto.response.enums.Type
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

    override suspend fun getCustomListsMangaDexAdminUser(): CollectionResponse<CustomListAttributes> =
        json.decodeFromString(readJSONFromAssets(context, "list_of_seasonalCustomLists.json"))

    override suspend fun getSeasonalMangaIds(): EntityResponse<Data<CustomListAttributes, List<DefaultRelationships>>> =
        json.decodeFromString(readJSONFromAssets(context, "SeasonalIdList.json"))


    override suspend fun getMangaListByIds(
        limit: Int,
        offset: Int,
        includes: List<String>,
        contentRating: List<ContentRating>,
        ids: List<String>,
        hasAvailableChapters: Boolean,
        latestUploadedChapter: String
    ): CollectionResponse<MangaAttributes> =
        json.decodeFromString(readJSONFromAssets(context, "SeasonalResponse_Limit10_Offset0.json"))

    override suspend fun getMangaById(
        id: String,
        includes: List<String>
    ): EntityResponse<DataIncludes<MangaAttributes>> =
        json.decodeFromString(readJSONFromAssets(context, "$id.json"))

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
        json.decodeFromString(readJSONFromAssets(context, "feed$mangaId.json"))

    override suspend fun getReadPages(chapterId: String): ReadResponse =
        json.decodeFromString(readJSONFromAssets(context, "59chapter-$chapterId.json"))

    override suspend fun getMangaCoverList(mangaId: String): CollectionResponseNotIncludes<DataWithoutRelationships<CoverAttributes>> =
        json.decodeFromString(readJSONFromAssets(context, "gluttonyCoverList.json"))

    override suspend fun getRecentlyAddedManga():
            CollectionResponse<MangaAttributes> =
        json.decodeFromString(readJSONFromAssets(context, "SeasonalResponse_Limit10_Offset0.json"))

    override suspend fun getLibraryStatus(accessToken: String): LibraryResponse =
        json.decodeFromString(readJSONFromAssets(context, "libraryStatusResponse.json"))

    override suspend fun getLatestUpdates(): CollectionResponse<ChapterAttributes> =
        json.decodeFromString(readJSONFromAssets(context, "getLatestUpdates.json"))

    override suspend fun getCoverListByMangaIds(
        limit: Int,
        offset: Int,
        mangaIds: List<String>
    ): CollectionResponse<CoverAttributes> = CollectionResponse(
        result = Result.OK,
        response = "collection",
        data = listOf(
            DataIncludes(
                id = "6c153bbd-5378-4a13-b71d-068a6bb18eef",
                type = Type.COVER_ART,
                attributes = CoverAttributes(
                    fileName = "0b67b0d6-9636-49ff-b9f5-9e8442f9e691.jpg",
                    volume = "2"
                ),
                relationships = listOf(
                    DefaultRelationships(
                        id = "649c7b09-dc44-488d-b758-55be3285640a",
                        type = Type.MANGA
                    ),
                    DefaultRelationships(
                        id = "f4e6597a-5a21-4f62-b0b3 bf20c7a2a865",
                        type = Type.USER
                    )
                )
            ),
            DataIncludes(
                id = "49d71a91-9d4e-45d0-9d3e-39ad5f0ca69e",
                type = Type.COVER_ART,
                attributes = CoverAttributes(
                    fileName = "3f70c90b-1565-4730-9b98-002379e1cb28.jpg",
                    volume = "1"
                ),
                relationships = listOf(
                    DefaultRelationships(
                        id = "3c025255-1151-4da4-9724-c28aa70e5062",
                        type = Type.MANGA
                    ),
                    DefaultRelationships(
                        id = "0035dd0f-c22d-4027-a933-8ae52e68e272",
                        type = Type.USER
                    )
                )
            ),
            DataIncludes(
                id = "a266bc29-63de-45e3-96bd-cc29b1c20091",
                type = Type.COVER_ART,
                attributes = CoverAttributes(
                    fileName = "f29ed130-d9bc-4c78-a032-df99bc15b6da.jpg",
                    volume = "0"
                ),
                relationships = listOf(
                    DefaultRelationships(
                        id = "3c025255-1151-4da4-9724-c28aa70e5062",
                        type = Type.MANGA
                    ),
                    DefaultRelationships(
                        id = "0035dd0f-c22d-4027-a933-8ae52e68e272",
                        type = Type.USER
                    )
                )
            ),
            DataIncludes(
                id = "74816c98-d5af-4010-bf48-91de7a2ac345",
                type = Type.COVER_ART,
                attributes = CoverAttributes(
                    fileName = "e5412bd1 - 6 a9c -4592 - 918 b -c5a997eb73d2.jpg".trim(),
                    volume = "1.1"
                ),
                relationships = listOf(
                    DefaultRelationships(
                        id = "8892 dc05 -867f - 4f ff -823 b -47210 a6746bb".trim(),
                        type = Type.MANGA
                    ),
                    DefaultRelationships(
                        id = "01642 be8 -72 b8 -48 ae -9199 - d8c747946ca5".trim(),
                        type = Type.USER
                    )
                )
            ),
            DataIncludes(
                id = "a68eb553 - d901 - 4693 - 88 b4 -e93d6ccd776f".trim(),
                type = Type.COVER_ART,
                attributes = CoverAttributes(
                    fileName = "be1e3c13 - 64 ce -4382 - a7ec - c92c11860c95.jpg".trim(),
                    volume = "1"
                ),
                relationships = listOf(
                    DefaultRelationships(
                        id = "8892 dc05 -867f - 4f ff -823 b -47210 a6746bb".trim(),
                        type = Type.MANGA
                    ),
                    DefaultRelationships(
                        id = "01642 be8 -72 b8 -48 ae -9199 - d8c747946ca5".trim(),
                        type = Type.USER
                    )
                )
            )
        )
    )


    private fun readJSONFromAssets(context: Context, path: String): String =
        context.assets.open(path).readBytes().toString(Charsets.UTF_8)
}