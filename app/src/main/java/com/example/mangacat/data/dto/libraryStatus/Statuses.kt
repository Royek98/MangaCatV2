package com.example.mangacat.data.dto.libraryStatus

import kotlinx.serialization.Serializable

@Serializable
data class Statuses(
    val mangaId: String,
    val statusType: String
)
