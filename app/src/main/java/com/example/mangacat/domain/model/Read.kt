package com.example.mangacat.domain.model

data class Read(
    val hash: String,
    val data: List<String>,
    val dataSaver: List<String>
)
