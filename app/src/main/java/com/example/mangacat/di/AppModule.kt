package com.example.mangacat.di

import com.example.mangacat.data.network.MangaDexApiService
import com.example.mangacat.data.repository.MangaDexRepositoryImpl
import com.example.mangacat.utils.AppConstants
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideMangaDexRepository(service: MangaDexApiService) = MangaDexRepositoryImpl(service)

    private val json = Json { ignoreUnknownKeys = true }

    @Singleton
    @Provides
    fun providesRetrofit(): MangaDexApiService {
        return Retrofit.Builder()
            .baseUrl(AppConstants.baseUrl)
            .addConverterFactory(
                json.asConverterFactory("application/json".toMediaType()))
            .build()
            .create(MangaDexApiService::class.java)
    }
}