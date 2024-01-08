package com.example.mangacat.utils

fun String.capitalized(): String {
    return this.lowercase().replaceFirstChar(Char::titlecase)
}

fun String.toTitle(): String =
    this.replace("_", " ").replaceFirstChar(Char::titlecase)
