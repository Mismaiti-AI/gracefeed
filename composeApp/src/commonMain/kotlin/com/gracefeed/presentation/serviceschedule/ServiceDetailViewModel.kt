package com.gracefeed.presentation.serviceschedule

import com.gracefeed.core.presentation.BaseViewModel
import com.gracefeed.domain.model.Service
import com.gracefeed.domain.usecase.ViewServiceDetailsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

sealed interface ServiceDetailUiState {
    data object Loading : ServiceDetailUiState
    data class Success(val service: Service) : ServiceDetailUiState
    data class Error(val message: String) : ServiceDetailUiState
}

class ServiceDetailViewModel(
    private val viewServiceDetailsUseCase: ViewServiceDetailsUseCase
) : BaseViewModel() {
    private val _uiState = MutableStateFlow<ServiceDetailUiState>(ServiceDetailUiState.Loading)
    val uiState: StateFlow<ServiceDetailUiState> = _uiState.asStateFlow()

    fun loadService(id: String) = safeLaunch {
        _uiState.value = ServiceDetailUiState.Loading
        val service = viewServiceDetailsUseCase(id)
        _uiState.value = if (service != null) {
            ServiceDetailUiState.Success(service)
        } else {
            ServiceDetailUiState.Error("Service not found")
        }
    }
}