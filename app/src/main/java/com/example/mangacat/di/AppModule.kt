package com.example.mangacat.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.example.mangacat.data.dto.Includes
import com.example.mangacat.data.dto.IncludesPolymorphicSerializer
import com.example.mangacat.data.fake.FakeRepositoryImpl
import com.example.mangacat.data.local.AuthPreferences
import com.example.mangacat.data.network.MangaDexApiService
import com.example.mangacat.data.repository.AuthRepositoryImpl
import com.example.mangacat.utils.AppConstants
import com.example.mangacat.utils.AppConstants.AUTH_PREFERENCES
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import javax.inject.Singleton

private val module = SerializersModule {
    polymorphicDefaultDeserializer(Includes::class ) { IncludesPolymorphicSerializer }
}

val appJson = Json {
    ignoreUnknownKeys = true
    serializersModule = module
}

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

//    @Singleton
//    @Provides
//    fun provideMangaDexApiService(service: MangaDexApiService) = MangaDexRepositoryImpl(service)

    @Singleton
    @Provides
    fun provideMangaDexFakeRepo(@ApplicationContext appContext: Context) = FakeRepositoryImpl(appContext)

    @Singleton
    @Provides
    fun provideAuthRepository(@ApplicationContext appContext: Context, preferences: AuthPreferences)
    = AuthRepositoryImpl(appContext, preferences)

    @Provides
    @Singleton
    fun providePreferenceDataStore(@ApplicationContext context: Context) : DataStore<Preferences> =
        PreferenceDataStoreFactory.create(
            produceFile = {
                context.preferencesDataStoreFile(AUTH_PREFERENCES)
            }
        )

    @Provides
    @Singleton
    fun provideAuthPreferences(dataStore: DataStore<Preferences>) =
        AuthPreferences(dataStore)

//    private val module = SerializersModule {
//        polymorphicDefaultDeserializer(Includes::class ) { IncludesPolymorphicSerializer }
//    }

//    val appJson = Json {
//        ignoreUnknownKeys = true
//        serializersModule = module
//    }

    @Singleton
    @Provides
    fun providesRetrofit(): MangaDexApiService {
        return Retrofit.Builder()
            .baseUrl(AppConstants.baseUrl)
            .addConverterFactory(
                appJson.asConverterFactory("application/json".toMediaType()))
            .build()
            .create(MangaDexApiService::class.java)
    }
}