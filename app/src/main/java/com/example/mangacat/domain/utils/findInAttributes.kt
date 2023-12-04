package com.example.mangacat.domain.utils

import com.example.mangacat.data.dto.AuthorIncludes
import com.example.mangacat.data.dto.CoverArtIncludes
import com.example.mangacat.data.dto.Includes
import com.example.mangacat.data.dto.ScanlationGroupIncludes
import com.example.mangacat.data.dto.UserIncludes
import com.example.mangacat.data.dto.response.enums.Type

//toDo change methods to return a proper Includes type instead of just string
fun findFileNameOfCoverInAttributes(relationships: List<Includes>): String {
    val coverAttributes= relationships.find { Type.COVER_ART == it.type } as CoverArtIncludes
    return coverAttributes.fileName
}

fun findAuthorInAttributes(relationships: List<Includes>): String {
    val authorAttributes = relationships.find { Type.AUTHOR == it.type } as AuthorIncludes
    return authorAttributes.name
}

fun findScanlationGroupInAttributes(relationships: List<Includes>?): ScanlationGroupIncludes? {
    val scanlationGroupAttributes = relationships?.find { Type.SCANLATION_GROUP == it.type } as ScanlationGroupIncludes?
    return scanlationGroupAttributes
}

fun findUserInAttributes(relationships: List<Includes>?): UserIncludes? {
    val userAttributes = relationships?.find { Type.USER == it.type } as UserIncludes?
    return userAttributes
}

//fun findScanlationGroupInAttributes(relationships: List<Includes>): String {
//    val scanlationGroupAttributes = relationships.find { Type.SCANLATION_GROUP == it.type } as ScanlationGroupIncludes
//    return scanlationGroupAttributes.
//}