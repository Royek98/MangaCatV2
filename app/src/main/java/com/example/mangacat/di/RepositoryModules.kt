package com.example.mangacat.di

import com.example.mangacat.data.fake.FakeAuthRepositoryImpl
import com.example.mangacat.data.fake.FakeRepositoryImpl
import com.example.mangacat.data.repository.AuthRepositoryImpl
import com.example.mangacat.data.repository.MangaDexRepositoryImpl
import com.example.mangacat.domain.repository.AuthRepository
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

    @Binds
    fun bindAuthRepositoryImpl(repository: AuthRepositoryImpl): AuthRepository


    //--------------------------------------------------------------------------------------------
    // FAKE REPOSITORIES
//    @Binds
//    fun bindFakeMangaDexRepositoryImpl(repository: FakeRepositoryImpl): MangaDexRepository

//    @Binds
//    fun bindAuthRepositoryImpl(repository: FakeAuthRepositoryImpl): AuthRepository
}