package com.example.mangacat.domain.usecase.manga

import android.util.Log
import com.example.mangacat.data.dto.MangaIncludes
import com.example.mangacat.data.dto.manga.enums.ContentRating
import com.example.mangacat.domain.repository.MangaDexRepository
import com.example.mangacat.domain.utils.findCoverInAttributes
import javax.inject.Inject

class GetRelatedMangaListCoverUseCase@Inject constructor(
    private val repository: MangaDexRepository
) {
    // Pair
    // first element - manga id
    // second element - cover id
    suspend operator fun invoke(idList: List<String>): List<Pair<String, String>> {
        val responseMangaList = repository.getMangaListByIds(
            100,
            0,
            listOf("cover_art"),
            listOf(
                ContentRating.SAFE,
                ContentRating.SUGGESTIVE,
                ContentRating.EROTICA,

                ),
            idList
        )
        val result = mutableListOf<Pair<String, String>>()
        responseMangaList.data.forEach {
            result.add(Pair(it.id, findCoverInAttributes(it.relationships!!).fileName))
        }

        return result
    }
}