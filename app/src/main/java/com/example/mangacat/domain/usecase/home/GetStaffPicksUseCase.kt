package com.example.mangacat.domain.usecase.home

import com.example.mangacat.data.dto.manga.MangaAttributes
import com.example.mangacat.data.dto.manga.enums.ContentRating
import com.example.mangacat.data.dto.response.CollectionResponse
import com.example.mangacat.domain.model.HomeMangaItem
import com.example.mangacat.domain.repository.MangaDexRepository
import com.example.mangacat.domain.utils.findCoverInAttributes
import javax.inject.Inject

class GetStaffPicksUseCase @Inject constructor(
    private val repository: MangaDexRepository
) {
    suspend operator fun invoke(): CollectionResponse<MangaAttributes> {
        val customLists = repository.getCustomListsMangaDexAdminUser()
        val staffPicksMangaList = customLists.data
            .find { it.attributes.name.contains("Staff Picks") }
            ?: throw Exception("Couldn't find a staff picks custom list")

        val mangaIds = staffPicksMangaList.relationships!!.map { it.id }

        return repository.getMangaListByIds(
            15,
            0,
            listOf("cover_art"),
            listOf(
                ContentRating.SAFE,
                ContentRating.SUGGESTIVE,
                ContentRating.EROTICA,
            ),
            mangaIds
        )
    }
}