package com.gracefeed.presentation.bulletinfeed

import com.gracefeed.core.presentation.BaseViewModel
import com.gracefeed.domain.model.Bulletin
import com.gracefeed.domain.usecase.ViewBulletinDetailsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

sealed interface BulletinDetailUiState {
    data object Loading : BulletinDetailUiState
    data class Success(val bulletin: Bulletin) : BulletinDetailUiState
    data class Error(val message: String) : BulletinDetailUiState
}

class BulletinDetailViewModel(
    private val viewBulletinDetailsUseCase: ViewBulletinDetailsUseCase
) : BaseViewModel() {
    private val _uiState = MutableStateFlow<BulletinDetailUiState>(BulletinDetailUiState.Loading)
    val uiState: StateFlow<BulletinDetailUiState> = _uiState.asStateFlow()

    fun loadBulletin(id: String) = safeLaunch {
        _uiState.value = BulletinDetailUiState.Loading
        val bulletin = viewBulletinDetailsUseCase(id)
        if (bulletin != null) {
            _uiState.value = BulletinDetailUiState.Success(bulletin)
        } else {
            _uiState.value = BulletinDetailUiState.Error("Bulletin not found")
        }
    }
}