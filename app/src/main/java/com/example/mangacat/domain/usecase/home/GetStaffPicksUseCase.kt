package com.example.mangacat.domain.usecase.home

import com.example.mangacat.data.dto.manga.enums.ContentRating
import com.example.mangacat.domain.model.HomeMangaItem
import com.example.mangacat.domain.repository.MangaDexRepository
import com.example.mangacat.domain.utils.findCoverInAttributes
import javax.inject.Inject

class GetStaffPicksUseCase @Inject constructor(
    private val repository: MangaDexRepository
) {
    suspend operator fun invoke(): List<HomeMangaItem> {
        val customLists = repository.getCustomListsMangaDexAdminUser()
        val staffPicksMangaList = customLists.data
            .find { it.attributes.name.contains("Staff Picks") }
            ?: throw Exception("Couldn't find a staff picks custom list")

        val mangaIds = staffPicksMangaList.relationships!!.map { it.id }

        val response = repository.getMangaListByIds(
            10,
            0,
            listOf("cover_art"),
            listOf(
                ContentRating.SAFE,
                ContentRating.SUGGESTIVE,
                ContentRating.EROTICA,
            ),
            mangaIds
        )

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