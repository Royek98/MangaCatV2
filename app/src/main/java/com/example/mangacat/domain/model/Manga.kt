package com.example.mangacat.domain.model

import com.example.mangacat.data.dto.English
import com.example.mangacat.data.dto.MangaIncludes
import com.example.mangacat.data.dto.manga.enums.ContentRating
import com.example.mangacat.data.dto.manga.enums.PublicationDemographic
import com.example.mangacat.data.dto.manga.enums.Status
import com.example.mangacat.data.dto.response.DataWithoutRelationships
import com.example.mangacat.data.dto.tag.TagAttributes

data class Manga(
    val id: String,
    val title: English,
    val description: English,
    val publicationDemographic: PublicationDemographic? = null,
    val contentRating: ContentRating,
    val status: Status,
    val year: Int? = null,
    val author: String,
    val artist: String,
    val cover: String,
    val genres: List<String?>,
    val themes: List<String?>,
    val format: List<String?>,
    val content: List<String?>,
    val related: List<MangaIncludes?>
)
