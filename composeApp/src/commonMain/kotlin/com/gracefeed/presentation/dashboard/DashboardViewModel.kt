package com.gracefeed.presentation.dashboard

import com.gracefeed.core.presentation.BaseViewModel
import com.gracefeed.core.presentation.UiState
import com.gracefeed.domain.usecase.GetDashboardOverviewUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

sealed interface DashboardUiState {
    data object Loading : DashboardUiState
    data class Success(
        val bulletinCount: Int,
        val upcomingEvents: List<com.gracefeed.domain.model.Event>,
        val latestSermons: List<com.gracefeed.domain.model.Sermon>,
        val activeServices: List<com.gracefeed.domain.model.Service>
    ) : DashboardUiState
    data class Error(val message: String) : DashboardUiState
}

class DashboardViewModel(
    private val getDashboardOverviewUseCase: GetDashboardOverviewUseCase
) : BaseViewModel() {
    val uiState: StateFlow<UiState<DashboardUiState>> = uiStateFrom(
        getDashboardOverviewUseCase()
    ) { overview ->
        DashboardUiState.Success(
            bulletinCount = overview.bulletinCount,
            upcomingEvents = overview.upcomingEvents,
            latestSermons = overview.latestSermons,
            activeServices = overview.activeServices
        )
    }

    init {
        loadDashboard()
    }
    
    fun loadDashboard() = safeLaunch {
        getDashboardOverviewUseCase.load()
    }
}