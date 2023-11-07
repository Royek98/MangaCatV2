package com.example.mangacat.model.manga

import com.example.mangacat.model.English
import com.example.mangacat.model.response.Data
import com.example.mangacat.model.manga.enums.ContentRating
import com.example.mangacat.model.manga.enums.PublicationDemographic
import com.example.mangacat.model.manga.enums.Status
import com.example.mangacat.model.Relationships
import com.example.mangacat.model.tag.TagAttributes
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
