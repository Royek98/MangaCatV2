package com.example.mangacat.domain.usecase.home

import com.example.mangacat.data.dto.manga.MangaAttributes
import com.example.mangacat.data.dto.response.CollectionResponse
import com.example.mangacat.domain.model.HomeMangaItem
import com.example.mangacat.domain.repository.MangaDexRepository
import com.example.mangacat.domain.utils.findCoverInAttributes
import javax.inject.Inject

class GetRecentlyAddedMangaUseCase @Inject constructor(
    private val repository: MangaDexRepository
) {
    suspend operator fun invoke(): CollectionResponse<MangaAttributes> =
        repository.getRecentlyAddedManga()

}