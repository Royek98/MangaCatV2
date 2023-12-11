package com.example.mangacat.domain.utils

import com.example.mangacat.data.dto.AuthorIncludes
import com.example.mangacat.data.dto.CoverArtIncludes
import com.example.mangacat.data.dto.Includes
import com.example.mangacat.data.dto.ScanlationGroupIncludes
import com.example.mangacat.data.dto.UserIncludes
import com.example.mangacat.data.dto.response.enums.Type

//toDo change methods to return a proper Includes type instead of just string
fun findCoverInAttributes(relationships: List<Includes>): CoverArtIncludes =
    relationships.find { Type.COVER_ART == it.type } as CoverArtIncludes

fun findAuthorInAttributes(relationships: List<Includes>): AuthorIncludes =
    relationships.find { Type.AUTHOR == it.type } as AuthorIncludes


fun findScanlationGroupInAttributes(relationships: List<Includes>?): ScanlationGroupIncludes? =
    relationships?.find { Type.SCANLATION_GROUP == it.type } as ScanlationGroupIncludes?

fun findUserInAttributes(relationships: List<Includes>?): UserIncludes? =
    relationships?.find { Type.USER == it.type } as UserIncludes?


//fun findScanlationGroupInAttributes(relationships: List<Includes>): String {
//    val scanlationGroupAttributes = relationships.find { Type.SCANLATION_GROUP == it.type } as ScanlationGroupIncludes
//    return scanlationGroupAttributes.
//}