package com.example.mangacat.domain.model

import com.example.mangacat.data.dto.chapter.ChapterAttributes
import com.example.mangacat.data.dto.response.CollectionResponse
import com.example.mangacat.data.dto.response.DataIncludes
import com.example.mangacat.data.dto.response.enums.Type
import com.example.mangacat.domain.utils.findRelatedMangaInAttributes
import com.example.mangacat.domain.utils.findScanlationGroupInAttributes
import com.example.mangacat.domain.utils.findUserInAttributes

data class Chapter(
    val chapterId: String,
    val mangaId: String,
    val volume: String,
    val chapterNumber: String,
    val chapterTitle: String?,
    val scanlationGroupName: String,
    val uploaderUsername: String,
    val updatedAt: String
) {
    constructor(chapter: DataIncludes<ChapterAttributes>): this(
        chapterId = chapter.id,
        mangaId = chapter.relationships?.find { it.type == Type.MANGA }!!.id,
        chapterNumber = chapter.attributes.chapter ?: "Chapter",
        volume = chapter.attributes.volume ?: "No vol",
        chapterTitle = chapter.attributes.title,
        scanlationGroupName = findScanlationGroupInAttributes(chapter.relationships)?.name ?: "",
        uploaderUsername = findUserInAttributes(chapter.relationships)?.username ?: "",
        updatedAt = chapter.attributes.updatedAt
    )

    companion object {
        fun toList(chapterList: CollectionResponse<ChapterAttributes>): List<Chapter> {
            val result = mutableListOf<Chapter>()
            chapterList.data.forEach { chapter ->
                result.add(
                    Chapter(chapter)
                )
            }
            return result
        }
    }
}
