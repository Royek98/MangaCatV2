package com.example.mangacat.di

import com.example.mangacat.data.network.MangaDexApiService
import com.example.mangacat.data.repository.FakeRepositoryImpl
//import com.example.mangacat.data.repository.MangaDexRepositoryImpl
import com.example.mangacat.utils.AppConstants
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.SerialName
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideMangaDexRepository(service: MangaDexApiService) = FakeRepositoryImpl(service)

    private val json = Json {
        ignoreUnknownKeys = true
    }

    @Singleton
    @Provides
    fun providesRetrofit(): MangaDexApiService {
        return Retrofit.Builder()
            .baseUrl(AppConstants.baseUrl)
            .addConverterFactory(
                json.asConverterFactory("application/json".toMediaType()))
            .addConverterFactory(EnumConverterFactory())
            .build()
            .create(MangaDexApiService::class.java)
    }
}

class EnumConverterFactory : Converter.Factory() {override fun stringConverter(
    type: Type,
    annotations: Array<Annotation>,
    retrofit: Retrofit
): Converter<Enum<*>, String>? =
    if (type is Class<*> && type.isEnum) {
        Converter { enum ->
            try {
                enum.javaClass.getField(enum.name).getAnnotation(SerialName::class.java)?.value
            } catch (exception: Exception) {
                null
            } ?: enum.toString()
        }
    } else {
        null
    }
}