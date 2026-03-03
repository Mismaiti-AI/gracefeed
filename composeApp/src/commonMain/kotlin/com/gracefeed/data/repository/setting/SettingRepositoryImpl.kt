package com.gracefeed.data.repository.setting

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import com.gracefeed.domain.model.AppSetting
import com.gracefeed.core.data.local.AppSettings

class SettingRepositoryImpl(private val appSettings: AppSettings) : SettingRepository {
    private val _items = MutableStateFlow<List<AppSetting>>(emptyList())
    override val items: StateFlow<List<AppSetting>> = _items.asStateFlow()
    private val _isLoading = MutableStateFlow(false)
    override val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    private val _error = MutableStateFlow<String?>(null)
    override val error: StateFlow<String?> = _error.asStateFlow()

    override suspend fun loadAll() {
        _isLoading.value = true
        try {
            // Load settings from AppSettings
            val darkModeEnabled = getBooleanSetting("dark_mode_enabled", false)
            val setting = AppSetting(
                id = "app_settings",
                darkModeEnabled = darkModeEnabled
            )
            _items.value = listOf(setting)
        } catch (e: Exception) {
            _error.value = e.message
        } finally {
            _isLoading.value = false
        }
    }

    override suspend fun getById(id: String): AppSetting? {
        return items.value.find { it.id == id }
    }

    override suspend fun insert(setting: AppSetting) {
        update(setting)
    }

    override suspend fun update(setting: AppSetting) {
        updateBooleanSetting("dark_mode_enabled", setting.darkModeEnabled)
        _items.value = listOf(setting)
    }

    override suspend fun delete(id: String) {
        // No-op for settings - we don't actually delete settings, just reset to default
    }

    override suspend fun updateSetting(key: String, value: String) {
        appSettings.putString(key, value)
    }

    override suspend fun getSetting(key: String, default: String): String {
        return appSettings.getString(key, default)
    }

    override suspend fun updateBooleanSetting(key: String, value: Boolean) {
        appSettings.putBoolean(key, value)
    }

    override suspend fun getBooleanSetting(key: String, default: Boolean): Boolean {
        return appSettings.getBoolean(key, default)
    }
}