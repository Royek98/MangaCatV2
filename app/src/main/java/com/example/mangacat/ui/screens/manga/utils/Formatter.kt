package com.example.mangacat.ui.screens.manga.utils

fun formatNumber(value: Int): String {
    return when {
        value >= 1_000_000 -> "${value / 1_000_000F}M"
        value >= 1_000 -> "${value / 1_000}K"
        else -> value.toString()
    }
}