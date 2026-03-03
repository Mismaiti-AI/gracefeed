package com.gracefeed.presentation.dashboard

import androidx.lifecycle.viewModelScope
import com.gracefeed.core.presentation.BaseViewModel
import com.gracefeed.core.presentation.UiState
import com.gracefeed.domain.usecase.GetDashboardOverviewUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
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
    val uiState: StateFlow<DashboardUiState> = combine(
        getDashboardOverviewUseCase(),
        isLoading,
        error
    ) { overview, loading, err ->
        when {
            err != null -> DashboardUiState.Error(err)
            loading -> DashboardUiState.Loading
            else -> DashboardUiState.Success(
                bulletinCount = overview.bulletinCount,
                upcomingEvents = overview.upcomingEvents,
                latestSermons = overview.latestSermons,
                activeServices = overview.activeServices
            )
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), DashboardUiState.Loading)

    init {
        loadDashboard()
    }

    fun loadDashboard() = safeLaunch {
        getDashboardOverviewUseCase.load()
    }
}
