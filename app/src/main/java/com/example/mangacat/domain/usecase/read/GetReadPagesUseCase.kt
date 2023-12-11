package com.example.mangacat.domain.usecase.read

import com.example.mangacat.domain.model.Read
import com.example.mangacat.domain.repository.MangaDexRepository
import javax.inject.Inject

class GetReadPagesUseCase@Inject constructor(
    private val repository: MangaDexRepository
) {
    suspend operator fun invoke(chapterId: String): Read {
        val response = repository.getReadPages(chapterId)

        return Read(hash = response.chapter.hash, data = response.chapter.data, dataSaver = response.chapter.dataSaver)
    }
}