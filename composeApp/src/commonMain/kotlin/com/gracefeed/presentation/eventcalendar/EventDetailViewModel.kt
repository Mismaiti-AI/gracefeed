package com.gracefeed.presentation.eventcalendar

import com.gracefeed.core.presentation.BaseViewModel
import com.gracefeed.domain.model.Event
import com.gracefeed.domain.usecase.ViewEventDetailsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

sealed interface EventDetailUiState {
    data object Loading : EventDetailUiState
    data class Success(val event: Event) : EventDetailUiState
    data class Error(val message: String) : EventDetailUiState
}

class EventDetailViewModel(
    private val viewEventDetailsUseCase: ViewEventDetailsUseCase
) : BaseViewModel() {
    private val _uiState = MutableStateFlow<EventDetailUiState>(EventDetailUiState.Loading)
    val uiState: StateFlow<EventDetailUiState> = _uiState.asStateFlow()

    fun loadEvent(id: String) = safeLaunch {
        _uiState.value = EventDetailUiState.Loading
        val event = viewEventDetailsUseCase(id)
        _uiState.value = if (event != null) {
            EventDetailUiState.Success(event)
        } else {
            EventDetailUiState.Error("Event not found")
        }
    }
}