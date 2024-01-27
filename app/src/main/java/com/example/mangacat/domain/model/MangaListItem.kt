package com.example.mangacat.domain.model

import com.example.mangacat.data.dto.manga.MangaAttributes
import com.example.mangacat.data.dto.manga.enums.ContentRating
import com.example.mangacat.data.dto.manga.enums.PublicationDemographic
import com.example.mangacat.data.dto.manga.enums.Status
import com.example.mangacat.data.dto.response.CollectionResponse
import com.example.mangacat.data.dto.response.DataIncludes
import com.example.mangacat.data.dto.tag.enums.TagGroup
import com.example.mangacat.domain.utils.findCoverInAttributes

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
) {
    constructor(manga: DataIncludes<MangaAttributes>) :
            this(
                id = manga.id,
                title = manga.attributes.title.en ?: "Title unknown",
                status = manga.attributes.status,
                contentRating = manga.attributes.contentRating,
                publicationDemographic = manga.attributes.publicationDemographic,
                description = manga.attributes.description.en ?: "Description unknown",
                cover = findCoverInAttributes(manga.relationships).fileName,
                genres = manga.attributes.tags.filter { it.attributes.group == TagGroup.GENRE }.map { it.attributes.name.en },
                themes = manga.attributes.tags.filter { it.attributes.group == TagGroup.THEME }.map { it.attributes.name.en },
                format = manga.attributes.tags.filter { it.attributes.group == TagGroup.FORMAT }.map { it.attributes.name.en },
                content = manga.attributes.tags.filter { it.attributes.group == TagGroup.CONTENT }.map { it.attributes.name.en },
            ) {

    }

    companion object {
        fun toList(mangaList: CollectionResponse<MangaAttributes>): List<MangaListItem> {
            val result = mutableListOf<MangaListItem>()
            mangaList.data.forEach { manga ->
                result.add(
                    MangaListItem(manga)
                )
            }
            return result
        }
    }
}
