package ru.hse.coursework.godaily.core.security

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore by preferencesDataStore(name = "user_prefs")

@Singleton
class JwtManager @Inject constructor(
    @ApplicationContext private val context: Context
) {

    private val JWT_KEY = stringPreferencesKey("jwt_key")

    suspend fun saveJwt(jwt: String) {
        context.dataStore.edit { prefs ->
            prefs[JWT_KEY] = jwt
        }
    }

    fun getJwt(): Flow<String?> {
        return context.dataStore.data.map { prefs ->
            prefs[JWT_KEY]
        }
    }

    suspend fun clearJwt() {
        context.dataStore.edit { prefs ->
            prefs.remove(JWT_KEY)
        }
    }
}
