package com.example.mangacat.data.network

import com.example.mangacat.data.dto.authentication.AuthRequest
import com.example.mangacat.data.dto.authentication.AuthResponse
import com.example.mangacat.data.dto.authentication.IsAuthenticatedResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import javax.inject.Singleton

@Singleton
interface AuthApiService {
    @FormUrlEncoded
    @POST("realms/mangadex/protocol/openid-connect/token")
    suspend fun authenticate(
        @Field("grant_type") grantType: String,
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("client_id") clientId: String,
        @Field("client_secret") clientSecret: String,
    ): AuthResponse

    @FormUrlEncoded
    @POST("realms/mangadex/protocol/openid-connect/token")
    suspend fun refresh(
        @Field("grant_type") grantType: String,
        @Field("refresh_token") refreshToken: String,
        @Field("client_id") clientId: String,
        @Field("client_secret") clientSecret: String,
    ): AuthResponse
}