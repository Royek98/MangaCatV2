package com.example.mangacat.domain.usecase.manga

import android.util.Log
import com.example.mangacat.domain.model.Chapter
import com.example.mangacat.domain.repository.MangaDexRepository
import javax.inject.Inject

class GetChapterListByMangaIdUserCase @Inject constructor(
    private val repository: MangaDexRepository
) {
    suspend operator fun invoke(mangaId: String): List<Chapter> {
        val response = repository.getChapterList(mangaId)

        val result = mutableListOf<Chapter>()

        response.data.forEach {manga ->
            result.add(
                Chapter(manga.attributes.chapter)
            )
        }

        return result
    }
}