package com.gracefeed.data.repository.setting

import kotlinx.coroutines.flow.StateFlow
import com.gracefeed.domain.model.AppSetting

interface SettingRepository {
    val items: StateFlow<List<AppSetting>>
    val isLoading: StateFlow<Boolean>
    val error: StateFlow<String?>
    suspend fun loadAll()
    suspend fun getById(id: String): AppSetting?
    suspend fun insert(setting: AppSetting)
    suspend fun update(setting: AppSetting)
    suspend fun delete(id: String)
    suspend fun updateSetting(key: String, value: String)
    suspend fun getSetting(key: String, default: String): String
    suspend fun updateBooleanSetting(key: String, value: Boolean)
    suspend fun getBooleanSetting(key: String, default: Boolean): Boolean
}