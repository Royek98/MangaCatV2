package com.example.mangacat.domain.usecase.list

import com.example.mangacat.data.dto.manga.enums.ContentRating
import com.example.mangacat.data.dto.tag.enums.TagGroup
import com.example.mangacat.domain.model.MangaListItem
import com.example.mangacat.domain.repository.MangaDexRepository
import com.example.mangacat.domain.utils.findCoverInAttributes
import javax.inject.Inject

class GetStaffPicksUseCase @Inject constructor(
    private val repository: MangaDexRepository
) {
    suspend operator fun invoke(): List<MangaListItem> {
        val responseIds = repository.getSeasonalMangaIds()
        val listOfRelationships = responseIds.data.relationships
        val listOfIds = listOfRelationships.map { it.id }
        val response = repository.getMangaListByIds(
            limit = 32,
            offset = 0,
            includes = listOf("cover_art"),
            contentRating = listOf(
                ContentRating.SAFE,
                ContentRating.SUGGESTIVE,
                ContentRating.EROTICA,

                ),
            ids = listOfIds
        )

        val result = mutableListOf<MangaListItem>()
        response.data.forEach { manga ->
            val tags = manga.attributes.tags
            val genres = tags.filter { it.attributes.group == TagGroup.GENRE }
            val themes = tags.filter { it.attributes.group == TagGroup.THEME }
            val format = tags.filter { it.attributes.group == TagGroup.FORMAT }
            val content = tags.filter { it.attributes.group == TagGroup.CONTENT }
            result.add(
                MangaListItem(
                    id = manga.id,
                    title = manga.attributes.title.en!!,
                    status = manga.attributes.status,
                    contentRating = manga.attributes.contentRating,
                    publicationDemographic = manga.attributes.publicationDemographic,
                    description = manga.attributes.description.en!!,
                    cover = findCoverInAttributes(manga.relationships!!).fileName,
                    genres = genres.map { it.attributes.name.en },
                    themes = themes.map { it.attributes.name.en },
                    format = format.map { it.attributes.name.en },
                    content = content.map { it.attributes.name.en }
                )
            )
        }
        return  result
    }
}