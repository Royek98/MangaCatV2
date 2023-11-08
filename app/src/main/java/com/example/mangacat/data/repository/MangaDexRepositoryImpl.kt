package com.example.mangacat.data.repository

import com.example.mangacat.data.FakeDataSource
import com.example.mangacat.data.dto.response.Response
import com.example.mangacat.data.dto.cutomList.CustomListAttributes
import com.example.mangacat.data.dto.manga.MangaAttributes
import com.example.mangacat.data.dto.Relationships
import com.example.mangacat.data.network.MangaDexApiService
import com.example.mangacat.domain.repository.MangaDexRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MangaDexRepositoryImpl @Inject constructor(
    private val service: MangaDexApiService
) : MangaDexRepository {
    //    override suspend fun getSeasonalManga(): MangaList = service.getSeasonalMangaIds()
    override suspend fun getSeasonalMangaIds(): Response<CustomListAttributes, Relationships> =
        FakeDataSource.seasonalList

    override suspend fun getMangaById(): Response<MangaAttributes, Relationships> =
        service.getMangaById()
}