package com.example.mangacat.domain.usecase.manga

import android.util.Log
import com.example.mangacat.domain.model.Chapter
import com.example.mangacat.domain.repository.MangaDexRepository
import com.example.mangacat.domain.utils.findScanlationGroupInAttributes
import com.example.mangacat.domain.utils.findUserInAttributes
import javax.inject.Inject

class GetChapterListByMangaIdUserCase @Inject constructor(
    private val repository: MangaDexRepository
) {
    suspend operator fun invoke(mangaId: String): List<Chapter> {
        val response = repository.getChapterList(mangaId)

        val result = mutableListOf<Chapter>()

        response.data.forEach {manga ->
            result.add(
                Chapter(
                    chapter = manga.attributes.chapter,
                    volume = manga.attributes.volume,
                    title = manga.attributes.title,
                    scanlationGroupName = findScanlationGroupInAttributes(manga.relationships)?.name ?: "",
                    uploaderUsername = findUserInAttributes(manga.relationships)?.username ?: "",
                    updatedAt = manga.attributes.updatedAt.toString()
                )
            )
        }

        return result
    }
}