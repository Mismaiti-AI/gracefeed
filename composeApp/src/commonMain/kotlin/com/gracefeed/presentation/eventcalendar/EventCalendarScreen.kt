package com.gracefeed.presentation.eventcalendar

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.compose.viewmodel.koinViewModel
import com.gracefeed.core.presentation.screens.GenericCalendarScreen
import com.gracefeed.core.data.calendar.CalendarDay
import com.gracefeed.core.data.calendar.CalendarEvent
import com.gracefeed.core.data.calendar.CalendarViewMode
import com.gracefeed.core.presentation.components.CalendarGrid
import com.gracefeed.core.presentation.components.CalendarEventCard
import com.gracefeed.core.presentation.components.AgendaList
import com.gracefeed.core.presentation.components.DaySchedule
import com.gracefeed.core.presentation.components.WeekStrip

@Composable
fun EventCalendarScreen(
    viewModel: EventCalendarViewModel = koinViewModel(),
    onItemClick: (String) -> Unit = {},
    onBackClick: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    when (val state = uiState) {
        is EventCalendarUiState.Loading -> {
            Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }
        is EventCalendarUiState.Success -> {
            // Convert domain events to calendar events for display
            val calendarEvents = state.events.map { event ->
                CalendarEvent(
                    id = event.id,
                    title = event.title,
                    startMillis = event.startDate.toEpochMilliseconds(),
                    endMillis = event.endDate.toEpochMilliseconds(),
                    description = event.description,
                    location = event.location
                )
            }
            
            // Generate calendar days (placeholder logic - would need actual calendar generation)
            val days = emptyList<CalendarDay>()
            
            GenericCalendarScreen(
                title = "Events",
                monthLabel = "Current Month",
                days = days,
                selectedDayEvents = calendarEvents,
                onDayClick = { /* Handle day click */ },
                onEventClick = { event -> onItemClick(event.id) },
                onRefresh = { viewModel.refresh() }
            )
        }
        is EventCalendarUiState.Error -> {
            // Handle error state - could show error message
            Box(modifier = Modifier.fillMaxSize()) {
                androidx.compose.material3.Text(
                    text = "Error: ${state.message}",
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}