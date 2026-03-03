package com.gracefeed.presentation.settings

import androidx.lifecycle.viewModelScope
import com.gracefeed.core.presentation.BaseViewModel
import com.gracefeed.core.presentation.UiState
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import com.gracefeed.domain.model.AppSetting
import com.gracefeed.domain.usecase.GetSettingsUseCase
import com.gracefeed.domain.usecase.UpdateSettingUseCase

sealed interface SettingsUiState {
    data object Loading : SettingsUiState
    data class Success(val settings: List<AppSetting>) : SettingsUiState
    data class Error(val message: String) : SettingsUiState
}

class SettingsViewModel(
    private val getSettingsUseCase: GetSettingsUseCase,
    private val updateSettingUseCase: UpdateSettingUseCase
) : BaseViewModel() {
    val uiState: StateFlow<SettingsUiState> = combine(
        getSettingsUseCase(),
        isLoading,
        error
    ) { settings, loading, err ->
        when {
            err != null -> SettingsUiState.Error(err)
            loading -> SettingsUiState.Loading
            else -> SettingsUiState.Success(settings = settings)
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), SettingsUiState.Loading)

    fun loadSettings() = safeLaunch { getSettingsUseCase.load() }
    fun refresh() = safeLaunch { getSettingsUseCase.load() }

    fun updateDarkMode(enabled: Boolean) = safeLaunch {
        val currentSettings = getSettingsUseCase().first().firstOrNull() ?: AppSetting()
        val updatedSetting = currentSettings.copy(darkModeEnabled = enabled)
        updateSettingUseCase(updatedSetting)
    }
}
