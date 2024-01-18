package com.example.mangacat.data.repository

import android.content.Context
import android.util.Log
import androidx.datastore.dataStoreFile
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.mangacat.data.dto.Includes
import com.example.mangacat.data.dto.IncludesPolymorphicSerializer
import com.example.mangacat.data.dto.ScanlationGroupIncludes
import com.example.mangacat.data.dto.authentication.AuthResponse
import com.example.mangacat.data.dto.authentication.IsAuthenticatedResponse
import com.example.mangacat.data.dto.response.Data
import com.example.mangacat.data.dto.response.EntityResponse
import com.example.mangacat.data.dto.user.UserAttributes
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
) : AuthRepository {

    private val module = SerializersModule {
        polymorphicDefaultDeserializer(Includes::class) { IncludesPolymorphicSerializer }
    }

    private val json = Json {
        ignoreUnknownKeys = true
        serializersModule = module
    }

    override suspend fun authenticate(username: String, password: String): Resource<AuthResponse> {
        return try {
            delay(1000)
            val response: AuthResponse =
                json.decodeFromString(readJSONFromAssets(context, "AuthResponse.json"))
            preferences.saveAuthToken(response)
            Resource.Success(response)
        } catch (e: IOException) {
            Resource.Error("${e.message}")
        } catch (e: HttpException) {
            Resource.Error("${e.message}")
        }
    }

    override suspend fun refresh(): Resource<AuthResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun check(): Resource<IsAuthenticatedResponse> {
        return try {
            Resource.Success(json.decodeFromString(readJSONFromAssets(context, "check.json")))
        } catch (e: IOException) {
            Resource.Error("${e.message}")
        }
    }

    override suspend fun getToken(): Flow<Token> {
        return preferences.getAuthToken()
    }

    override suspend fun getAuthenticatedUserInformation(accessToken: String):
            EntityResponse<Data<UserAttributes, List<ScanlationGroupIncludes>>> {
        return json.decodeFromString(readJSONFromAssets(context, "MeResponse.json"))
    }

    private fun readJSONFromAssets(context: Context, path: String): String =
        context.assets.open(path).readBytes().toString(Charsets.UTF_8)
}