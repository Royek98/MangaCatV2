package com.example.mangacat.domain.usecase.home

import android.util.Log
import com.example.mangacat.data.dto.cutomList.CustomListAttributes
import com.example.mangacat.data.dto.manga.enums.ContentRating
import com.example.mangacat.data.dto.manga.enums.PublicationDemographic
import com.example.mangacat.data.dto.response.CollectionResponse
import com.example.mangacat.data.dto.response.DataWithoutRelationships
import com.example.mangacat.data.dto.tag.TagAttributes
import com.example.mangacat.data.dto.tag.enums.TagGroup
import com.example.mangacat.domain.model.HomeSeasonalMangaItem
import com.example.mangacat.domain.repository.MangaDexRepository
import com.example.mangacat.domain.utils.findCoverInAttributes
import com.example.mangacat.utils.capitalized
import java.time.LocalDate
import javax.inject.Inject

class GetSeasonalUseCase @Inject constructor(
    private val repository: MangaDexRepository
) {

    suspend operator fun invoke(): List<HomeSeasonalMangaItem> {
        val listOfSeasonalCustomLists = repository.getCustomListsMangaDexAdminUser()

        val foundList = findCurrentSeasonList(listOfSeasonalCustomLists)

        val responseMangaList = repository.getMangaListByIds(
            10,
            0,
            listOf("cover_art"),
            listOf(
                ContentRating.SAFE,
                ContentRating.SUGGESTIVE,
                ContentRating.EROTICA,
            ),
            foundList
        )
        val dataMangaList = responseMangaList.data
        Log.d("TAG", "invoke: $responseMangaList")

        val result = mutableListOf<HomeSeasonalMangaItem>()
        dataMangaList.forEach { manga ->
            result.add(
                HomeSeasonalMangaItem(
                    manga.id,
                    findCoverInAttributes(manga.relationships!!).fileName,
                    setTags(
                        manga.attributes.publicationDemographic,
                        manga.attributes.contentRating,
                        manga.attributes.tags
                    )
                )
            )
        }
        return result
    }

    private fun findCurrentSeasonList(
        listOfSeasonalCustomLists: CollectionResponse<CustomListAttributes>
    ): List<String> {
        // seasons by broadcasting anime standards
        // JANUARY returns 1 that's why element 0 is ""
        val seasons: List<String> = listOf(
            "", "Winter", "Winter", "Winter", "Spring", "Spring", "Spring",
            "Summer", "Summer", "Summer", "Fall", "Fall", "Fall"
        )
        val currentDate = LocalDate.now()
        val currentSeason = seasons[currentDate.month.value]

        var foundList = listOfSeasonalCustomLists.data
            .find {
                it.attributes.name.contains("Seasonal: $currentSeason ${currentDate.year}")
            }

        // if the list for current season not exists then take previous one
        if (foundList == null) {
            // from previous month
            if (currentDate.month.value > 1) {
                foundList = listOfSeasonalCustomLists.data
                    .find {
                        it.attributes.name.contains(
                            "Seasonal: ${seasons[currentDate.month.value - 1]} ${currentDate.year}"
                        )
                    }
            }
            // from previous year (December)
            else {
                foundList = listOfSeasonalCustomLists.data
                    .find {
                        it.attributes.name.contains(
                            "Seasonal: ${seasons[12]} ${currentDate.year - 1}"
                        )
                    }
            }
        }
        return foundList?.relationships!!.map { it.id }
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
        val genres =
            tags.filter { it.attributes.group == TagGroup.GENRE }.map { it.attributes.name.en }
        for (i in genres.indices) {
            resultTags.add(genres[i]!!)
            if (resultTags.size == 3) return resultTags
        }

        // if there is not enough genres then add themes to display
        val themes =
            tags.filter { it.attributes.group == TagGroup.THEME }.map { it.attributes.name.en }
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