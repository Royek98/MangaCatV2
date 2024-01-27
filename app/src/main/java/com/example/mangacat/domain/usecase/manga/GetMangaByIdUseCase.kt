package com.example.mangacat.domain.usecase.manga

import com.example.mangacat.data.dto.response.enums.Type
import com.example.mangacat.domain.model.Manga
import com.example.mangacat.domain.repository.MangaDexRepository
import javax.inject.Inject

class GetMangaByIdUseCase @Inject constructor(
    private val repository: MangaDexRepository
) {
    suspend operator fun invoke(mangaId: String): Manga {
        val response = repository.getMangaById(
            id = mangaId,
            includes = listOf(
                Type.ARTIST.name.lowercase(),
                Type.AUTHOR.name.lowercase(),
                Type.COVER_ART.name.lowercase()
            )
        )

        return Manga(
            response.data
        )
    }

}