package com.egsi.labsi.sog.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.egsi.labsi.sog.model.CheckHistory
import com.egsi.labsi.sog.model.FavoriteProducer
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import java.util.*

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "egglabel_preferences")

class PreferencesManager(private val context: Context) {
    
    companion object {
        private val RECENT_CHECKS_KEY = stringPreferencesKey("recent_checks")
        private val FAVORITES_KEY = stringPreferencesKey("favorites")
        private val DARK_MODE_KEY = booleanPreferencesKey("dark_mode")
        private val LANGUAGE_KEY = stringPreferencesKey("language")
        private val NOTIFICATIONS_KEY = booleanPreferencesKey("notifications")
    }
    
    // Recent Checks
    suspend fun saveRecentChecks(checks: List<CheckHistory>) {
        val json = checks.joinToString("|") { check ->
            "${check.id}|${check.code}|${check.eggCode.category.code}|${check.eggCode.housingType.code}|${check.eggCode.country}|${check.eggCode.producer}|${check.eggCode.status.name}|${check.timestamp.time}"
        }
        context.dataStore.edit { preferences ->
            preferences[RECENT_CHECKS_KEY] = json
        }
    }
    
    fun getRecentChecks(): Flow<List<CheckHistory>> = context.dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            val json = preferences[RECENT_CHECKS_KEY] ?: ""
            if (json.isEmpty()) {
                emptyList()
            } else {
                json.split("|").chunked(8).mapNotNull { parts ->
                    if (parts.size == 8) {
                        try {
                            CheckHistory(
                                id = parts[0],
                                code = parts[1],
                                eggCode = createEggCodeFromParts(parts[2], parts[3], parts[4], parts[5], parts[6]),
                                timestamp = Date(parts[7].toLong())
                            )
                        } catch (e: Exception) {
                            null
                        }
                    } else null
                }
            }
        }
    
    suspend fun clearRecentChecks() {
        context.dataStore.edit { preferences ->
            preferences.remove(RECENT_CHECKS_KEY)
        }
    }
    
    // Favorites
    suspend fun saveFavorites(favorites: List<FavoriteProducer>) {
        val json = favorites.joinToString("|") { favorite ->
            "${favorite.id}|${favorite.producer}|${favorite.country}|${favorite.addedDate.time}"
        }
        context.dataStore.edit { preferences ->
            preferences[FAVORITES_KEY] = json
        }
    }
    
    fun getFavorites(): Flow<List<FavoriteProducer>> = context.dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            val json = preferences[FAVORITES_KEY] ?: ""
            if (json.isEmpty()) {
                emptyList()
            } else {
                json.split("|").chunked(4).mapNotNull { parts ->
                    if (parts.size == 4) {
                        try {
                            FavoriteProducer(
                                id = parts[0],
                                producer = parts[1],
                                country = parts[2],
                                addedDate = Date(parts[3].toLong())
                            )
                        } catch (e: Exception) {
                            null
                        }
                    } else null
                }
            }
        }
    
    // Settings
    suspend fun saveDarkMode(enabled: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[DARK_MODE_KEY] = enabled
        }
    }
    
    fun getDarkMode(): Flow<Boolean> = context.dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            preferences[DARK_MODE_KEY] ?: false
        }
    
    suspend fun saveLanguage(language: String) {
        context.dataStore.edit { preferences ->
            preferences[LANGUAGE_KEY] = language
        }
    }
    
    fun getLanguage(): Flow<String> = context.dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            preferences[LANGUAGE_KEY] ?: "English"
        }
    
    suspend fun saveNotificationsEnabled(enabled: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[NOTIFICATIONS_KEY] = enabled
        }
    }
    
    fun getNotificationsEnabled(): Flow<Boolean> = context.dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            preferences[NOTIFICATIONS_KEY] ?: true
        }
    
    suspend fun clearAllData() {
        context.dataStore.edit { preferences ->
            preferences.clear()
        }
    }
    
    private fun createEggCodeFromParts(
        categoryCode: String,
        housingCode: String,
        country: String,
        producer: String,
        statusName: String
    ): com.egsi.labsi.sog.model.EggCode {
        val category = com.egsi.labsi.sog.model.EggCategory.values().find { it.code == categoryCode } ?: com.egsi.labsi.sog.model.EggCategory.C3
        val housing = com.egsi.labsi.sog.model.HousingType.values().find { it.code == housingCode } ?: com.egsi.labsi.sog.model.HousingType.CAGE
        val status = com.egsi.labsi.sog.model.EggStatus.valueOf(statusName)
        
        return com.egsi.labsi.sog.model.EggCode(
            code = "Unknown",
            category = category,
            housingType = housing,
            country = country,
            producer = producer,
            expiryDate = null,
            status = status
        )
    }
}
