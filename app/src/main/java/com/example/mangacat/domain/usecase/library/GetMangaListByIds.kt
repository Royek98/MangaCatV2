package com.example.mangacat.domain.usecase.library

import com.example.mangacat.data.dto.manga.enums.ContentRating
import com.example.mangacat.data.dto.response.enums.Type
import com.example.mangacat.data.dto.tag.enums.TagGroup
import com.example.mangacat.domain.model.Manga
import com.example.mangacat.domain.repository.MangaDexRepository
import javax.inject.Inject

class GetMangaListByIds @Inject constructor(
    private val repository: MangaDexRepository
) {
    suspend operator fun invoke(ids: List<String>): List<Manga> {
        val response = repository.getMangaListByIds(
            limit = 10,
            offset = 0,
            includes = listOf(
                Type.COVER_ART.name.lowercase()
            ),
            contentRating = listOf(
                ContentRating.SAFE,
                ContentRating.SUGGESTIVE,
                ContentRating.EROTICA,
            ),
            ids = ids,
        )

        val result = mutableListOf<Manga>()
        response.data.forEach {

            result.add(
                Manga(
                    id = it.id,
                    title = it.attributes.title,
                    description = it.attributes.description,
                    publicationDemographic = it.attributes.publicationDemographic,
                    contentRating = it.attributes.contentRating,
                    status = it.attributes.status,
                    year = it.attributes.year,
                    author = "Author",
                    artist = "Artist",
                    cover = "filename",
                    genres = it.attributes.tags.filter { it.attributes.group == TagGroup.GENRE }
                        .map { it.attributes.name.en },
                    themes = it.attributes.tags.filter { it.attributes.group == TagGroup.THEME }
                        .map { it.attributes.name.en },
                    format = it.attributes.tags.filter { it.attributes.group == TagGroup.FORMAT }
                        .map { it.attributes.name.en },
                    content = it.attributes.tags.filter { it.attributes.group == TagGroup.CONTENT }
                        .map { it.attributes.name.en },
                    related = emptyList()
                )
            )
        }

        return result
    }
}