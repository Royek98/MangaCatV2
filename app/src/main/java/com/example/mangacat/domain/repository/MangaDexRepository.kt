package com.example.mangacat.domain.repository

import com.example.mangacat.data.dto.Relationships
import com.example.mangacat.data.dto.cutomList.CustomListAttributes
import com.example.mangacat.data.dto.manga.MangaAttributes
import com.example.mangacat.data.dto.response.Response

interface MangaDexRepository {
    suspend fun getSeasonalMangaIds(): Response<CustomListAttributes, Relationships>
    suspend fun getMangaById(): Response<MangaAttributes, Relationships>
}