package com.example.mangacat.data.dto.manga

import com.example.mangacat.data.dto.English
import com.example.mangacat.data.dto.response.Data
import com.example.mangacat.data.dto.manga.enums.ContentRating
import com.example.mangacat.data.dto.manga.enums.PublicationDemographic
import com.example.mangacat.data.dto.manga.enums.Status
import com.example.mangacat.data.dto.Relationships
import com.example.mangacat.data.dto.tag.TagAttributes
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("attributes")
data class MangaAttributes(
    val title: English,
    val description: English,
    val publicationDemographic: PublicationDemographic,
    val contentRating: ContentRating,
    val status: Status,
    val year: Int,
    val tags: List<Data<TagAttributes, Relationships>>
)
