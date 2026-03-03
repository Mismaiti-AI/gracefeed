package com.gracefeed.presentation.bulletinfeed

import androidx.lifecycle.viewModelScope
import com.gracefeed.core.presentation.BaseViewModel
import com.gracefeed.core.presentation.UiState
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.combine
import com.gracefeed.domain.model.Bulletin
import com.gracefeed.domain.usecase.GetBulletinListUseCase

sealed interface BulletinListUiState {
    data object Loading : BulletinListUiState
    data class Success(val bulletins: List<Bulletin>) : BulletinListUiState
    data class Error(val message: String) : BulletinListUiState
}

class BulletinListViewModel(
    private val getBulletinListUseCase: GetBulletinListUseCase
) : BaseViewModel() {
    val uiState: StateFlow<BulletinListUiState> = combine(
        getBulletinListUseCase(),
        isLoading,
        error
    ) { bulletins, loading, err ->
        when {
            err != null -> BulletinListUiState.Error(err)
            loading -> BulletinListUiState.Loading
            else -> BulletinListUiState.Success(bulletins = bulletins)
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), BulletinListUiState.Loading)

    init { loadBulletins() }
    
    fun loadBulletins() = safeLaunch { getBulletinListUseCase.load() }
    fun refresh() = safeLaunch { getBulletinListUseCase.load() }
}