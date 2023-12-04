package com.example.mangacat.di

import android.content.Context
import com.example.mangacat.data.dto.Includes
import com.example.mangacat.data.dto.IncludesPolymorphicSerializer
import com.example.mangacat.data.fake.FakeRepositoryImpl
import com.example.mangacat.data.network.MangaDexApiService
import com.example.mangacat.data.repository.MangaDexRepositoryImpl
import com.example.mangacat.utils.AppConstants
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.SerialName
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

//    @Singleton
//    @Provides
//    fun provideMangaDexApiService(service: MangaDexApiService) = MangaDexRepositoryImpl(service)

    @Singleton
    @Provides
    fun provideMangaDexFakeRepo(@ApplicationContext appContext: Context) = FakeRepositoryImpl(appContext)



    private val module = SerializersModule {
        polymorphicDefaultDeserializer(Includes::class ) { IncludesPolymorphicSerializer }
    }

    private val json = Json {
        ignoreUnknownKeys = true
        serializersModule = module
    }

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