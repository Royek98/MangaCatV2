package com.example.mangacat.domain.usecase.manga

import com.example.mangacat.domain.model.Chapter
import com.example.mangacat.domain.repository.MangaDexRepository
import javax.inject.Inject

class GetChapterListByMangaIdUserCase @Inject constructor(
    private val repository: MangaDexRepository
) {
    suspend operator fun invoke(mangaId: String): List<Chapter> {
        val response = repository.getChapterListByMangaId(mangaId = mangaId)

        return Chapter.toList(response)
    }
}