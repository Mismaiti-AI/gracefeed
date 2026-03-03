package com.gracefeed.presentation.eventcalendar

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.compose.viewmodel.koinViewModel
import com.gracefeed.core.presentation.screens.GenericDetailScreen
import com.gracefeed.core.presentation.components.DetailCard
import com.gracefeed.core.presentation.components.InfoRow
import com.gracefeed.core.presentation.components.StatusBadge
import com.gracefeed.domain.model.Event

@Composable
fun EventDetailScreen(
    eventId: String,
    viewModel: EventDetailViewModel = koinViewModel(),
    onBackClick: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    when (val state = uiState) {
        is EventDetailUiState.Loading -> {
            Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }
        is EventDetailUiState.Success -> {
            GenericDetailScreen(
                title = state.event.title,
                item = state.event,
                isLoading = false,
                onBackClick = onBackClick,
                detailContent = { event ->
                    DetailCard {
                        InfoRow(label = "Start Date", value = event.startDate.toString())
                        InfoRow(label = "End Date", value = event.endDate.toString())
                        InfoRow(label = "Location", value = event.location)
                        InfoRow(label = "Category", value = event.category)
                        InfoRow(label = "Description", value = event.description)
                        StatusBadge(text = if (event.isRecurring) "Recurring" else "One-time")
                        if (event.hasRsvp && event.rsvpLink.isNotEmpty()) {
                            InfoRow(label = "RSVP Link", value = event.rsvpLink)
                        }
                    }
                }
            )
        }
        is EventDetailUiState.Error -> {
            Box(modifier = Modifier.fillMaxSize()) {
                Text(
                    text = "Error: ${state.message}",
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}