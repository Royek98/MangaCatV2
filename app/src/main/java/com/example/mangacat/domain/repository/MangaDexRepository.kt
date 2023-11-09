package com.example.mangacat.domain.repository

import com.example.mangacat.data.dto.Relationships
import com.example.mangacat.data.dto.cutomList.CustomListAttributes
import com.example.mangacat.data.dto.manga.MangaAttributes
import com.example.mangacat.data.dto.manga.enums.ContentRating
import com.example.mangacat.data.dto.response.CollectionResponse
import com.example.mangacat.data.dto.response.EntityResponse

interface MangaDexRepository {
    suspend fun getSeasonalMangaIds(): EntityResponse<CustomListAttributes, Relationships>
    suspend fun getMangaListByIds(
        limit: Int,
        offset: Int,
        includes: List<String>,
        contentRating: List<ContentRating>,
        ids: List<String>
    ): CollectionResponse<MangaAttributes, Relationships>
}