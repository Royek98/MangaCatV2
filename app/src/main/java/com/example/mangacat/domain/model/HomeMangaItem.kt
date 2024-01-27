package com.example.mangacat.domain.model

import com.example.mangacat.data.dto.manga.MangaAttributes
import com.example.mangacat.data.dto.response.CollectionResponse
import com.example.mangacat.data.dto.response.DataIncludes
import com.example.mangacat.domain.utils.findCoverInAttributes

data class HomeMangaItem(
    val id: String,
    val title: String,
    val cover: String
) {

    constructor(manga: DataIncludes<MangaAttributes>): this(
        id = manga.id,
        title = manga.attributes.title.en ?: "No title",
        cover = findCoverInAttributes(manga.relationships).fileName
    )

    companion object {
        fun toList(mangaList: CollectionResponse<MangaAttributes>): List<HomeMangaItem> {
            val result = mutableListOf<HomeMangaItem>()
            mangaList.data.forEach { manga ->
                result.add(
                    HomeMangaItem(manga)
                )
            }
            return result
        }
    }
}
