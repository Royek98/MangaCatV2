package com.example.mangacat.data.local

import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import com.example.mangacat.data.dto.authentication.AuthResponse
import com.example.mangacat.domain.model.Token
import com.example.mangacat.utils.AppConstants.ACCESS_TOKEN
import com.example.mangacat.utils.AppConstants.REFRESH_TOKEN
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthPreferences @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {
    suspend fun saveAuthToken(token: AuthResponse) {
        dataStore.edit { pref ->
            token.accessToken?.let {
                pref[ACCESS_TOKEN] = it
            }
            token.refreshToken?.let {
                pref[REFRESH_TOKEN] = it
            }
        }
    }

    suspend fun clearAuthToken() {
        dataStore.edit { it.clear() }
    }

    suspend fun getAuthToken(): Token? =
        dataStore.data
            .catch { emptyPreferences() }
            .map { pref ->
                Token(accessToken = pref[ACCESS_TOKEN], refreshToken = pref[REFRESH_TOKEN])
            }.firstOrNull()
}