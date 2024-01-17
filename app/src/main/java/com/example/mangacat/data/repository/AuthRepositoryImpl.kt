package com.example.mangacat.data.repository

import android.content.Context
import androidx.datastore.dataStoreFile
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.mangacat.data.dto.Includes
import com.example.mangacat.data.dto.IncludesPolymorphicSerializer
import com.example.mangacat.data.dto.authentication.AuthResponse
import com.example.mangacat.data.local.AuthPreferences
import com.example.mangacat.data.network.Resource
import com.example.mangacat.domain.model.Token
import com.example.mangacat.domain.repository.AuthRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl(
    private val context: Context,
    private val preferences: AuthPreferences
): AuthRepository {

    private val module = SerializersModule {
        polymorphicDefaultDeserializer(Includes::class) { IncludesPolymorphicSerializer }
    }

    private val json = Json {
        ignoreUnknownKeys = true
        serializersModule = module
    }
    override suspend fun authenticate(username: String, password: String): Resource<AuthResponse> {
        return try {
            delay(2000)
            val response: AuthResponse = json.decodeFromString(readJSONFromAssets(context, "AuthResponse.json"))
//            Resource.Success(Token(
//                accessToken = response.accessToken,
//                refreshToken = response.refreshToken
//            ))
            preferences.saveAuthToken(response)
            Resource.Success(response)
        } catch (e: IOException){
            Resource.Error("${e.message}")
        }catch (e: HttpException){
            Resource.Error("${e.message}")
        }
    }

    override suspend fun getToken(): Flow<Token> {
        return preferences.getAuthToken()
    }

    private fun readJSONFromAssets(context: Context, path: String): String =
        context.assets.open(path).readBytes().toString(Charsets.UTF_8)
}