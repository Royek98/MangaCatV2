package com.example.mangacat.domain.usecase.manga

import android.util.Log
import com.example.mangacat.domain.model.Cover
import com.example.mangacat.domain.repository.MangaDexRepository
import javax.inject.Inject

class GetMangaCoverListUseCase @Inject constructor(
    private val repository: MangaDexRepository
) {
    suspend operator fun invoke(mangaId: String): List<Cover> {
        val response = repository.getMangaCoverList(mangaId)
        val coverList = mutableListOf<Cover>()
        response.data.forEach {
            coverList.add(
                Cover(fileName = it.attributes.fileName, volume = it.attributes.volume)
            )
        }

        return coverList
    }
}
