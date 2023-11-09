package com.example.mangacat.data.repository

import com.example.mangacat.data.dto.response.EntityResponse
import com.example.mangacat.data.dto.cutomList.CustomListAttributes
import com.example.mangacat.data.dto.manga.MangaAttributes
import com.example.mangacat.data.dto.Relationships
import com.example.mangacat.data.network.MangaDexApiService
import com.example.mangacat.domain.repository.MangaDexRepository
import javax.inject.Inject
import javax.inject.Singleton

//@Singleton
//class MangaDexRepositoryImpl @Inject constructor(
//    private val service: MangaDexApiService
//) : MangaDexRepository {
//    override suspend fun getSeasonalMangaIds(): EntityResponse<CustomListAttributes, Relationships> = service.getSeasonalMangaIds()
//
//    override suspend fun getMangaListByIds(): EntityResponse<MangaAttributes, Relationships> =
//        service.getMangaListByIds()
//}