package com.example.mangacat.data.fake

import android.content.Context
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
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Singleton

@Singleton
class FakeAuthRepositoryImpl(
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

    override suspend fun authenticate(username: String, password: String): AuthResponse {
        return json.decodeFromString(readJSONFromAssets(context, "AuthResponse.json"))
//        preferences.saveAuthToken(response)
//        return response
    }

    override suspend fun refresh(refreshToken: String): AuthResponse {
        TODO("Not yet implemented")
    }

    override suspend fun check(token: String): IsAuthenticatedResponse {
        return json.decodeFromString(readJSONFromAssets(context, "check.json"))
    }

    override suspend fun getToken(): Token? {
        return preferences.getAuthToken()
    }

    override suspend fun getAuthenticatedUserInformation(accessToken: String):
            EntityResponse<Data<UserAttributes, List<ScanlationGroupIncludes>>> {
        return json.decodeFromString(readJSONFromAssets(context, "MeResponse.json"))
    }

    override suspend fun clearToken() {
        TODO("Not yet implemented")
    }

    override suspend fun saveTokenLocally(token: Token) {
        TODO("Not yet implemented")
    }

    private fun readJSONFromAssets(context: Context, path: String): String =
        context.assets.open(path).readBytes().toString(Charsets.UTF_8)
}