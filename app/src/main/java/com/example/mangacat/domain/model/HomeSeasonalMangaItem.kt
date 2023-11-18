package com.example.mangacat.domain.model

data class HomeSeasonalMangaItem(
    val id: String,
    val cover: String,
    // publicationDemographic ?: content rating, 2 tags
    val tags: List<String?> = List(3) { "" }
)

