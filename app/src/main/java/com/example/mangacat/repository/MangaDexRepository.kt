package com.example.mangacat.repository

import com.example.mangacat.data.FakeDataSource
import com.example.mangacat.model.response.Response
import com.example.mangacat.model.cutomList.CustomListAttributes
import com.example.mangacat.model.manga.MangaAttributes
import com.example.mangacat.model.Relationships
import com.example.mangacat.network.MangaDexService
import javax.inject.Inject
import javax.inject.Singleton

interface MangaDexRepository {
    suspend fun getSeasonalMangaIds(): Response<CustomListAttributes, Relationships>
    suspend fun getMangaById(): Response<MangaAttributes, Relationships>
}

@Singleton
class MangaDexRepositoryImpl @Inject constructor(
    private val service: MangaDexService
) : MangaDexRepository {
    //    override suspend fun getSeasonalManga(): MangaList = service.getSeasonalMangaIds()
    override suspend fun getSeasonalMangaIds(): Response<CustomListAttributes, Relationships> =
        FakeDataSource.seasonalList

    override suspend fun getMangaById(): Response<MangaAttributes, Relationships> =
        service.getMangaById()
}