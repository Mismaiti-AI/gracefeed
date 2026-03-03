package com.gracefeed.presentation.serviceschedule

import androidx.lifecycle.viewModelScope
import com.gracefeed.core.presentation.BaseViewModel
import com.gracefeed.core.presentation.UiState
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.combine
import com.gracefeed.domain.model.Service
import com.gracefeed.domain.usecase.GetServiceListUseCase

sealed interface ServiceListUiState {
    data object Loading : ServiceListUiState
    data class Success(val services: List<Service>) : ServiceListUiState
    data class Error(val message: String) : ServiceListUiState
}

class ServiceListViewModel(
    private val getServiceListUseCase: GetServiceListUseCase
) : BaseViewModel() {
    val uiState: StateFlow<ServiceListUiState> = combine(
        getServiceListUseCase(),
        isLoading,
        error
    ) { services, loading, err ->
        when {
            err != null -> ServiceListUiState.Error(err)
            loading -> ServiceListUiState.Loading
            else -> ServiceListUiState.Success(services = services)
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ServiceListUiState.Loading)

    init { loadServices() }
    fun loadServices() = safeLaunch { getServiceListUseCase.load() }
    fun refresh() = safeLaunch { getServiceListUseCase.load() }
}