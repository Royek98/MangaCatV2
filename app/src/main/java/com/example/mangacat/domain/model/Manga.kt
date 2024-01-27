package com.example.mangacat.domain.model

import com.example.mangacat.data.dto.English
import com.example.mangacat.data.dto.MangaIncludes
import com.example.mangacat.data.dto.manga.MangaAttributes
import com.example.mangacat.data.dto.manga.enums.ContentRating
import com.example.mangacat.data.dto.manga.enums.PublicationDemographic
import com.example.mangacat.data.dto.manga.enums.Status
import com.example.mangacat.data.dto.response.DataIncludes
import com.example.mangacat.data.dto.tag.enums.TagGroup
import com.example.mangacat.domain.utils.findArtistInAttributes
import com.example.mangacat.domain.utils.findAuthorInAttributes
import com.example.mangacat.domain.utils.findCoverInAttributes
import com.example.mangacat.domain.utils.findRelatedMangaInAttributes

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
) {
    constructor(manga: DataIncludes<MangaAttributes>) : this(
        id = manga.id,
        title = manga.attributes.title,
        description = manga.attributes.description,
        publicationDemographic = manga.attributes.publicationDemographic,
        contentRating = manga.attributes.contentRating,
        status = manga.attributes.status,
        year = manga.attributes.year,
        author = findAuthorInAttributes(manga.relationships).name,
        artist = findArtistInAttributes(manga.relationships).name,
        cover = findCoverInAttributes(manga.relationships).fileName,
        genres = manga.attributes.tags.filter { it.attributes.group == TagGroup.GENRE }
            .map { it.attributes.name.en },
        themes = manga.attributes.tags.filter { it.attributes.group == TagGroup.THEME }
            .map { it.attributes.name.en },
        format = manga.attributes.tags.filter { it.attributes.group == TagGroup.FORMAT }
            .map { it.attributes.name.en },
        content = manga.attributes.tags.filter { it.attributes.group == TagGroup.CONTENT }
            .map { it.attributes.name.en },
        related = findRelatedMangaInAttributes(manga.relationships)
    )
}
