package com.example.disasteralert.helper

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SettingPreferences private constructor(private val dataStore: DataStore<Preferences>) {

    private val _THEMEKEY = booleanPreferencesKey("theme_setting")
    private val _FILTERKEY = stringPreferencesKey("filter_setting")

    fun getThemeSetting(): Flow<Boolean> {
        return dataStore.data.map { preferences ->
            preferences[_THEMEKEY] ?: false
        }
    }

    suspend fun saveThemeSetting(isDarkModeActive: Boolean) {
        dataStore.edit { preferences ->
            preferences[_THEMEKEY] = isDarkModeActive
        }
    }

    fun getLatestFilter(): Flow<String> {
        return dataStore.data.map { preferences ->
            preferences[_FILTERKEY] ?: ""
        }
    }

    suspend fun saveLatestFilter(latestFilter: String) {
        dataStore.edit { preferences ->
            preferences[_FILTERKEY] = latestFilter
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: SettingPreferences? = null

        fun getInstance(dataStore: DataStore<Preferences>): SettingPreferences {
            return INSTANCE ?: synchronized(this) {
                val instance = SettingPreferences(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}