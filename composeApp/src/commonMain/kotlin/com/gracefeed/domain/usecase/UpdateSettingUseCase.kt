package com.gracefeed.domain.usecase

import com.gracefeed.domain.model.AppSetting
import com.gracefeed.data.repository.setting.SettingRepository

class UpdateSettingUseCase(private val repository: SettingRepository) {
    suspend operator fun invoke(setting: AppSetting) = repository.update(setting)
}