package com.example.mangacat.domain.utils

import com.example.mangacat.data.dto.AuthorIncludes
import com.example.mangacat.data.dto.CoverArtIncludes
import com.example.mangacat.data.dto.Includes
import com.example.mangacat.data.dto.response.enums.Type

fun findFileNameOfCoverInAttributes(relationships: List<Includes>): String {
    val coverAttributes= relationships.find { Type.COVER_ART == it.type } as CoverArtIncludes
    return coverAttributes.fileName
}

fun findAuthorInAttributes(relationships: List<Includes>): String {
    val authorAttributes = relationships.find { Type.AUTHOR == it.type } as AuthorIncludes
    return authorAttributes.name
}