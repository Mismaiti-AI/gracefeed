package com.gracefeed.domain.usecase

import kotlinx.coroutines.flow.StateFlow
import com.gracefeed.domain.model.AppSetting
import com.gracefeed.data.repository.setting.SettingRepository

class GetSettingsUseCase(private val repository: SettingRepository) {
    operator fun invoke(): StateFlow<List<AppSetting>> = repository.items
    suspend fun load() { repository.loadAll() }
}