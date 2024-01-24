package com.example.mangacat.domain.usecase.home

import com.example.mangacat.domain.model.HomeMangaItem
import com.example.mangacat.domain.repository.MangaDexRepository
import com.example.mangacat.domain.utils.findCoverInAttributes
import javax.inject.Inject

class GetRecentlyAddedMangaUseCase @Inject constructor(
    private val repository: MangaDexRepository
) {
    suspend operator fun invoke(): List<HomeMangaItem> {
        val response = repository.getRecentlyAddedManga()
        val mangaResult = mutableListOf<HomeMangaItem>()
        response.data.forEach { manga ->
            mangaResult.add(
                HomeMangaItem(
                    id = manga.id,
                    title = manga.attributes.title.en!!,
                    cover = findCoverInAttributes(manga.relationships!!).fileName
                )
            )
        }
        return mangaResult
    }
}