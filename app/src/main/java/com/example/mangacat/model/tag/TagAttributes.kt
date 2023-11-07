package com.example.mangacat.model.tag

import com.example.mangacat.model.English
import com.example.mangacat.model.tag.enums.TagGroup
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("attributes")
data class TagAttributes(
    val name: English,
    val group: TagGroup
)
