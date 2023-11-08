package com.example.mangacat.di

import com.example.mangacat.data.repository.MangaDexRepositoryImpl
import com.example.mangacat.domain.repository.MangaDexRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModules {
    @Binds
    fun bindMangaDexRepositoryImpl(repository: MangaDexRepositoryImpl): MangaDexRepository

}