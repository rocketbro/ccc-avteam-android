package com.asherpope.ccc_av_team_android.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.asherpope.ccc_av_team_android.data.images.ImageAspectRatio
import com.asherpope.ccc_av_team_android.data.images.ImageSize
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AppPreferencesManager(private val context: Context) {
    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "app_preferences")

        val PULL_DATA_KEY = booleanPreferencesKey("pull_data")
        val DATA_FETCHED_KEY = booleanPreferencesKey("data_fetched")
        val IMAGE_ASPECT_RATIO_KEY = stringPreferencesKey("image_aspect_ratio")
        val GLOBAL_IMAGE_SIZE_KEY = stringPreferencesKey("global_image_size")
        val USER_AUTHENTICATED_KEY = booleanPreferencesKey("user_authenticated")
    }

    val pullData: Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[PULL_DATA_KEY] ?: true
    }

    val dataFetched: Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[DATA_FETCHED_KEY] ?: true
    }

    val imageAspectRatio: Flow<String> = context.dataStore.data.map { preferences ->
        preferences[IMAGE_ASPECT_RATIO_KEY] ?: ImageAspectRatio.STANDARD.name
    }

    val globalImageSize: Flow<String> = context.dataStore.data.map { preferences ->
        preferences[GLOBAL_IMAGE_SIZE_KEY] ?: ImageSize.MEDIUM.name
    }

    val userAuthenticated: Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[USER_AUTHENTICATED_KEY] ?: false
    }

    suspend fun updatePullData(value: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[PULL_DATA_KEY] = value
        }
    }

    suspend fun updateDataFetched(value: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[DATA_FETCHED_KEY] = value
        }
    }

    suspend fun updateImageAspectRatio(value: String) {
        context.dataStore.edit { preferences ->
            preferences[IMAGE_ASPECT_RATIO_KEY] = value
        }
    }

    suspend fun updateGlobalImageSize(value: String) {
        context.dataStore.edit { preferences ->
            preferences[GLOBAL_IMAGE_SIZE_KEY] = value
        }
    }

    suspend fun updateUserAuthenticated(value: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[USER_AUTHENTICATED_KEY] = value
        }
    }
}
