package com.example.mangacat.domain.usecase.library

import com.example.mangacat.domain.repository.MangaDexRepository
import javax.inject.Inject

class GetLibraryStatusesUseCase @Inject constructor(
    private val repository: MangaDexRepository
) {
    suspend operator fun invoke(accessToken: String): LinkedHashMap<String, List<String>> {
        val response = repository.getLibraryStatus(accessToken)

        val idsGroupedByStatus = LinkedHashMap<String, MutableList<String>>()
        response.statuses.groupByTo(idsGroupedByStatus, {it.statusType}, {it.mangaId})

        return linkedMapOf(
            *idsGroupedByStatus.entries.map { (key, value) ->
                key to value
            }.toTypedArray()
        )
    }
}