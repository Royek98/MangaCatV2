package com.example.mangacat.domain.usecase.home

import com.example.mangacat.data.dto.CoverArtIncludes
import com.example.mangacat.data.dto.Includes
import com.example.mangacat.data.dto.manga.enums.ContentRating
import com.example.mangacat.data.dto.manga.enums.PublicationDemographic
import com.example.mangacat.data.dto.response.DataWithoutRelationships
import com.example.mangacat.data.dto.response.enums.Type
import com.example.mangacat.data.dto.tag.TagAttributes
import com.example.mangacat.data.dto.tag.enums.TagGroup
import com.example.mangacat.domain.model.HomeSeasonalMangaItem
import com.example.mangacat.domain.repository.MangaDexRepository
import com.example.mangacat.domain.utils.findFileNameOfCoverInAttributes
import com.example.mangacat.utils.capitalized
import javax.inject.Inject

class GetSeasonalUseCase @Inject constructor(
    private val repository: MangaDexRepository
) {

    suspend operator fun invoke(): List<HomeSeasonalMangaItem> {
        val responseIds = repository.getSeasonalMangaIds()

        val listOfRelationships = responseIds.data.relationships
        val listOfIds = listOfRelationships.map { it.id }
        val responseMangaList = repository.getMangaListByIds(
            10,
            0,
            listOf("cover_art"),
            listOf(
                ContentRating.SAFE,
                ContentRating.SUGGESTIVE,
                ContentRating.EROTICA,

                ),
            listOfIds
        )
        val dataMangaList = responseMangaList.data

        val result = mutableListOf<HomeSeasonalMangaItem>()
        dataMangaList.forEach { manga->
            result.add(
                HomeSeasonalMangaItem(
                    manga.id,
                    findFileNameOfCoverInAttributes(manga.relationships!!),
                    setTags(manga.attributes.publicationDemographic, manga.attributes.contentRating, manga.attributes.tags)
                )
            )
        }
        return result
    }

    private fun setTags(
        publicationDemographic: PublicationDemographic?,
        contentRating: ContentRating,
        tags: List<DataWithoutRelationships<TagAttributes>>
    ): List<String?> {
        val resultTags = mutableListOf<String?>()
        if (publicationDemographic != null) resultTags.add(publicationDemographic.name.capitalized())
        if (contentRating != ContentRating.SAFE) resultTags.add(contentRating.name.capitalized())


        // add genres to display
        val genres = tags.filter { it.attributes.group == TagGroup.GENRE }.map { it.attributes.name.en }
        for (i in genres.indices) {
            resultTags.add(genres[i]!!)
            if (resultTags.size == 3) return resultTags
        }

        // if there is not enough genres then add themes to display
        val themes = tags.filter { it.attributes.group == TagGroup.THEME }.map { it.attributes.name.en }
        for (i in themes.indices) {
            resultTags.add(themes[i]!!)
            if (resultTags.size == 3) return resultTags
        }

        // if there is not enough themes then just add nulls
        while (resultTags.size < 3) {
            resultTags.add(null)
        }

        return resultTags
    }
}