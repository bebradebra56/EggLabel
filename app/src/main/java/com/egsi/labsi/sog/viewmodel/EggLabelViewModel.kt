package com.egsi.labsi.sog.viewmodel

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.egsi.labsi.sog.data.EggCodeRepository
import com.egsi.labsi.sog.data.PreferencesManager
import com.egsi.labsi.sog.model.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.util.*

class EggLabelViewModel(private val context: Context) : ViewModel() {
    private val repository = EggCodeRepository()
    private val preferencesManager = PreferencesManager(context)
    
    // UI State
    var searchQuery by mutableStateOf("")
        private set
    
    var currentDecodedEgg by mutableStateOf<EggCode?>(null)
        private set
    
    // Recent checks
    private val _recentChecks = MutableStateFlow<List<CheckHistory>>(emptyList())
    val recentChecks: StateFlow<List<CheckHistory>> = _recentChecks.asStateFlow()
    
    // Favorites
    private val _favorites = MutableStateFlow<List<FavoriteProducer>>(emptyList())
    val favorites: StateFlow<List<FavoriteProducer>> = _favorites.asStateFlow()
    
    // Settings
    var isDarkMode by mutableStateOf(false)
        private set
    
    var selectedLanguage by mutableStateOf("English")
        private set
    
    var notificationsEnabled by mutableStateOf(true)
        private set
    
    init {
        loadData()
    }
    
    private fun loadData() {
        viewModelScope.launch {
            // Load recent checks
            preferencesManager.getRecentChecks().collect { checks ->
                _recentChecks.value = checks
            }
            
            // Load favorites
            preferencesManager.getFavorites().collect { favs ->
                _favorites.value = favs
            }
            
            // Load settings
            preferencesManager.getDarkMode().collect { darkMode ->
                isDarkMode = darkMode
            }
            
            preferencesManager.getLanguage().collect { language ->
                selectedLanguage = language
            }
            
            preferencesManager.getNotificationsEnabled().collect { notifications ->
                notificationsEnabled = notifications
            }
        }
    }
    
    fun updateSearchQuery(query: String) {
        searchQuery = query
    }
    
    fun decodeEggCode(code: String) {
        viewModelScope.launch {
            val decodedEgg = repository.decodeEggCode(code)
            if (decodedEgg != null) {
                currentDecodedEgg = decodedEgg
                
                // Add to recent checks
                val newCheck = CheckHistory(
                    id = UUID.randomUUID().toString(),
                    code = code,
                    eggCode = decodedEgg,
                    timestamp = Date()
                )
                
                val updatedChecks = listOf(newCheck) + _recentChecks.value.take(9) // Keep last 10
                _recentChecks.value = updatedChecks
                preferencesManager.saveRecentChecks(updatedChecks)
            }
        }
    }
    
    fun addToFavorites(eggCode: EggCode) {
        viewModelScope.launch {
            val favorite = FavoriteProducer(
                id = UUID.randomUUID().toString(),
                producer = eggCode.producer,
                country = eggCode.country,
                addedDate = Date()
            )
            
            val updatedFavorites = listOf(favorite) + _favorites.value
            _favorites.value = updatedFavorites
            preferencesManager.saveFavorites(updatedFavorites)
        }
    }
    
    fun removeFromFavorites(favoriteId: String) {
        viewModelScope.launch {
            val updatedFavorites = _favorites.value.filter { it.id != favoriteId }
            _favorites.value = updatedFavorites
            preferencesManager.saveFavorites(updatedFavorites)
        }
    }
    
    fun clearRecentChecks() {
        viewModelScope.launch {
            _recentChecks.value = emptyList()
            preferencesManager.clearRecentChecks()
        }
    }
    
    fun updateDarkMode(enabled: Boolean) {
        isDarkMode = enabled
        viewModelScope.launch {
            preferencesManager.saveDarkMode(enabled)
        }
    }
    
    fun updateLanguage(language: String) {
        selectedLanguage = language
        viewModelScope.launch {
            preferencesManager.saveLanguage(language)
        }
    }
    
    fun updateNotifications(enabled: Boolean) {
        notificationsEnabled = enabled
        viewModelScope.launch {
            preferencesManager.saveNotificationsEnabled(enabled)
        }
    }
    
    fun clearAllData() {
        viewModelScope.launch {
            _recentChecks.value = emptyList()
            _favorites.value = emptyList()
            preferencesManager.clearAllData()
        }
    }
}
