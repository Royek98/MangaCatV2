package com.example.mangacat.domain.usecase.library

import com.example.mangacat.domain.repository.MangaDexRepository
import javax.inject.Inject

class GetLibraryStatusesUseCase @Inject constructor(
    private val repository: MangaDexRepository
) {
    suspend operator fun invoke(): LinkedHashMap<String, MutableList<String>> {
        val response = repository.getLibraryStatus()

        val idsGroupedByStatus = LinkedHashMap<String, MutableList<String>>()
        response.statuses.groupByTo(idsGroupedByStatus, {it.statusType}, {it.mangaId})

        return idsGroupedByStatus
    }
}