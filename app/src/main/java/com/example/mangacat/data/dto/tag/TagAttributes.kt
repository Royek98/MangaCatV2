package com.example.mangacat.data.dto.tag

import com.example.mangacat.data.dto.English
import com.example.mangacat.data.dto.tag.enums.TagGroup
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("attributes")
data class TagAttributes(
    val name: English,
    val group: TagGroup
)
