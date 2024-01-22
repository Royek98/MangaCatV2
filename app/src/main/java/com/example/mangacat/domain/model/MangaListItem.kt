package com.example.mangacat.domain.model

import com.example.mangacat.data.dto.manga.enums.ContentRating
import com.example.mangacat.data.dto.manga.enums.PublicationDemographic
import com.example.mangacat.data.dto.manga.enums.Status

data class MangaListItem(
    val id: String,
    val title: String,
    val status: Status,
    val contentRating: ContentRating,
    val publicationDemographic: PublicationDemographic? = null,
    val description: String,
    val cover: String,
    val genres: List<String?>,
    val themes: List<String?>,
    val format: List<String?>,
    val content: List<String?>,
)
