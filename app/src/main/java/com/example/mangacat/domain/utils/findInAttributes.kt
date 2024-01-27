package com.example.mangacat.domain.utils

import android.util.Log
import com.example.mangacat.data.dto.AuthorIncludes
import com.example.mangacat.data.dto.CoverArtIncludes
import com.example.mangacat.data.dto.Includes
import com.example.mangacat.data.dto.MangaIncludes
import com.example.mangacat.data.dto.ScanlationGroupIncludes
import com.example.mangacat.data.dto.UserIncludes
import com.example.mangacat.data.dto.response.enums.Type
import com.example.mangacat.data.dto.tag.TagAttributes
import com.example.mangacat.domain.model.Manga

//toDo change methods to return a proper Includes type instead of just string
fun findCoverInAttributes(relationships: List<Includes>?): CoverArtIncludes =
    relationships?.find { Type.COVER_ART == it.type } as CoverArtIncludes

fun findAuthorInAttributes(relationships: List<Includes>?): AuthorIncludes =
    relationships?.find { Type.AUTHOR == it.type } as AuthorIncludes

fun findArtistInAttributes(relationships: List<Includes>?): AuthorIncludes =
    relationships?.find { Type.ARTIST == it.type } as AuthorIncludes

fun findRelatedMangaInAttributes(relationships: List<Includes>?): List<MangaIncludes?> =
    relationships?.filter { Type.MANGA == it.type } as List<MangaIncludes?>



fun findScanlationGroupInAttributes(relationships: List<Includes>?): ScanlationGroupIncludes? =
    relationships?.find { Type.SCANLATION_GROUP == it.type } as ScanlationGroupIncludes?

fun findUserInAttributes(relationships: List<Includes>?): UserIncludes? =
    relationships?.find { Type.USER == it.type } as UserIncludes?


//fun findScanlationGroupInAttributes(relationships: List<Includes>): String {
//    val scanlationGroupAttributes = relationships.find { Type.SCANLATION_GROUP == it.type } as ScanlationGroupIncludes
//    return scanlationGroupAttributes.
//}