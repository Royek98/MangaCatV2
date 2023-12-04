package com.example.mangacat.domain.usecase.manga

import com.example.mangacat.data.dto.AuthorIncludes
import com.example.mangacat.data.dto.CoverArtIncludes
import com.example.mangacat.data.dto.Includes
import com.example.mangacat.data.dto.response.enums.Type
import com.example.mangacat.domain.model.Manga
import com.example.mangacat.domain.repository.MangaDexRepository
import com.example.mangacat.domain.utils.findAuthorInAttributes
import com.example.mangacat.domain.utils.findFileNameOfCoverInAttributes
import javax.inject.Inject

class GetMangaByIdUseCase @Inject constructor(
    private val repository: MangaDexRepository
) {
    suspend operator fun invoke(mangaId: String): Manga {
        val response = repository.getMangaById(mangaId)

        return Manga(
            id = response.data.id,
            title = response.data.attributes.title,
            description = response.data.attributes.description,
            publicationDemographic = response.data.attributes.publicationDemographic,
            contentRating = response.data.attributes.contentRating,
            status = response.data.attributes.status,
            year = response.data.attributes.year,
            author = findAuthorInAttributes(response.data.relationships!!),
            cover = findFileNameOfCoverInAttributes(response.data.relationships)
        )
    }

}