package com.example.mangacat.di

import com.example.mangacat.network.MangaDexService
import com.example.mangacat.repository.MangaDexRepositoryImpl
import com.example.mangacat.utils.AppConstants
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideMangaDexRepository(service: MangaDexService) = MangaDexRepositoryImpl(service)

    @Singleton
    @Provides
    fun providesRetrofit(): MangaDexService {
        return Retrofit.Builder()
            .baseUrl(AppConstants.baseUrl)
            .addConverterFactory(
                Json{ignoreUnknownKeys = true}.asConverterFactory("application/json".toMediaType()))
            .build()
            .create(MangaDexService::class.java)
    }
}