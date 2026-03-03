package com.gracefeed.presentation.eventcalendar

import com.gracefeed.core.presentation.BaseViewModel
import com.gracefeed.core.presentation.UiState
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.combine
import com.gracefeed.domain.model.Event
import com.gracefeed.domain.usecase.GetEventListUseCase

sealed interface EventCalendarUiState {
    data object Loading : EventCalendarUiState
    data class Success(val events: List<Event>) : EventCalendarUiState
    data class Error(val message: String) : EventCalendarUiState
}

class EventCalendarViewModel(
    private val getEventListUseCase: GetEventListUseCase
) : BaseViewModel() {
    val uiState: StateFlow<EventCalendarUiState> = combine(
        getEventListUseCase(),
        isLoading,
        error
    ) { events, loading, err ->
        when {
            err != null -> EventCalendarUiState.Error(err)
            loading -> EventCalendarUiState.Loading
            else -> EventCalendarUiState.Success(events = events)
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), EventCalendarUiState.Loading)

    init { loadEvents() }
    fun loadEvents() = safeLaunch { getEventListUseCase.load() }
    fun refresh() = safeLaunch { getEventListUseCase.load() }
}