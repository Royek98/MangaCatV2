package com.example.mangacat.repository

import com.example.mangacat.model.mangaList.MangaList
import com.example.mangacat.network.MangaDexService
import javax.inject.Inject

class MangaDexRepository @Inject constructor (
    private val service: MangaDexService
) {
    suspend fun getSeasonalManga(): MangaList = service.getSeasonalManga()

}