package com.example.mangacat.domain.model

import com.example.mangacat.data.dto.English
import com.example.mangacat.data.dto.MangaIncludes
import com.example.mangacat.data.dto.manga.enums.ContentRating
import com.example.mangacat.data.dto.manga.enums.PublicationDemographic
import com.example.mangacat.data.dto.manga.enums.Status
import com.example.mangacat.data.dto.response.DataWithoutRelationships
import com.example.mangacat.data.dto.tag.TagAttributes

data class Manga(
    val id: String = "",
    val title: English = English(""),
    val description: English = English(""),
    val publicationDemographic: PublicationDemographic? = null,
    val contentRating: ContentRating = ContentRating.SAFE,
    val status: Status = Status.COMPLETED,
    val year: Int? = null,
    val author: String = "",
    val artist: String = "",
    val cover: String = "",
    val genres: List<String?> = listOf(),
    val themes: List<String?> = listOf(),
    val format: List<String?> = listOf(),
    val related: List<MangaIncludes?> = listOf()
)
