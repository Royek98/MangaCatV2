package com.example.mangacat.di

import com.example.mangacat.repository.MangaDexRepository
import com.example.mangacat.repository.MangaDexRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModules {
    @Binds
    fun provideMangaDexRepositoryImpl(repository: MangaDexRepositoryImpl): MangaDexRepository

}