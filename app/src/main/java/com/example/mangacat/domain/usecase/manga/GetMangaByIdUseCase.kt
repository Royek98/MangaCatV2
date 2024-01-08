package com.example.mangacat.domain.usecase.manga

import com.example.mangacat.data.dto.tag.enums.TagGroup
import com.example.mangacat.domain.model.Manga
import com.example.mangacat.domain.repository.MangaDexRepository
import com.example.mangacat.domain.utils.findArtistInAttributes
import com.example.mangacat.domain.utils.findAuthorInAttributes
import com.example.mangacat.domain.utils.findCoverInAttributes
import com.example.mangacat.domain.utils.findRelatedMangaInAttributes
import javax.inject.Inject

class GetMangaByIdUseCase @Inject constructor(
    private val repository: MangaDexRepository
) {
    suspend operator fun invoke(mangaId: String): Manga {
        val response = repository.getMangaById(mangaId)
        val tags = response.data.attributes.tags
        val genres = tags.filter { it.attributes.group == TagGroup.GENRE }
        val themes = tags.filter { it.attributes.group == TagGroup.THEME }
        val format = tags.filter { it.attributes.group == TagGroup.FORMAT }

        return Manga(
            id = response.data.id,
            title = response.data.attributes.title,
            description = response.data.attributes.description,
            publicationDemographic = response.data.attributes.publicationDemographic,
            contentRating = response.data.attributes.contentRating,
            status = response.data.attributes.status,
            year = response.data.attributes.year,
            author = findAuthorInAttributes(response.data.relationships!!).name,
            cover = findCoverInAttributes(response.data.relationships).fileName,
            artist = findArtistInAttributes(response.data.relationships).name,
            related = findRelatedMangaInAttributes(response.data.relationships),
            genres = genres.map { it.attributes.name.en },
            themes = themes.map { it.attributes.name.en },
            format = format.map { it.attributes.name.en }
        )
    }

}