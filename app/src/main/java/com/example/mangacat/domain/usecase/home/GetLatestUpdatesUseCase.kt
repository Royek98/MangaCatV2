package com.example.mangacat.domain.usecase.home

import android.util.Log
import com.example.mangacat.data.dto.MangaIncludes
import com.example.mangacat.data.dto.manga.enums.ContentRating
import com.example.mangacat.data.dto.response.enums.Type
import com.example.mangacat.domain.model.HomeLatestUpdate
import com.example.mangacat.domain.repository.MangaDexRepository
import com.example.mangacat.domain.utils.findRelatedMangaInAttributes
import com.example.mangacat.domain.utils.findScanlationGroupInAttributes
import com.example.mangacat.domain.utils.findUserInAttributes
import javax.inject.Inject

class GetLatestUpdatesUseCase @Inject constructor(
    private val repository: MangaDexRepository
) {
    suspend operator fun invoke(): List<HomeLatestUpdate> {
        val chaptersResponse = repository.getLatestUpdates()

        val result = mutableListOf<HomeLatestUpdate>()

        val coverInfo = mutableListOf<CoverInfo>()
        chaptersResponse.data.forEach { chapter ->
            chapter.relationships?.let { relationship ->
                val relatedManga = findRelatedMangaInAttributes(relationship)[0]
                val relatedGroup = findScanlationGroupInAttributes(relationship)
                val relatedUser = findUserInAttributes(relationship)

                coverInfo.add(
                    CoverInfo(mangaId = relatedManga!!.id)
                )

                result.add(
                    HomeLatestUpdate(
                        mangaId = relatedManga.id,
                        chapterId = chapter.id,
                        title = relatedManga.title.en ?: "",
                        chapterTitle = "Chapter ${chapter.attributes.chapter}. ${chapter.attributes.title ?: ""}",
                        groupName = relatedGroup?.name,
                        userName = relatedUser?.username
                    )
                )
            }
        }

        val coverResponse = repository.getCoverListByMangaIds(mangaIds = coverInfo.map { it.mangaId })

        coverResponse.data.forEach {
            it.relationships?.let { relationship ->
                val mangaId = relationship.find { it.type == Type.MANGA }?.id
                result.find { it.mangaId == mangaId }?.cover = it.attributes.fileName
            }
        }
        return result
    }

    private data class CoverInfo(
        val mangaId: String,
        var cover: String? = null
    )
}