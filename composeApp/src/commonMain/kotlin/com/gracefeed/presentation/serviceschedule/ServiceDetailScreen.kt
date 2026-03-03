package com.gracefeed.presentation.serviceschedule

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
import com.gracefeed.core.presentation.components.DetailRow

@Composable
fun ServiceDetailScreen(
    serviceId: String,
    viewModel: ServiceDetailViewModel = koinViewModel(),
    onBackClick: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    when (val state = uiState) {
        is ServiceDetailUiState.Loading -> {
            Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }
        is ServiceDetailUiState.Success -> GenericDetailScreen(
            title = state.service.serviceName,
            item = state.service,
            onBackClick = onBackClick,
            detailContent = { service ->
                DetailCard(
                    title = "Service Info",
                    rows = buildList {
                        add(DetailRow(label = "Time", value = service.regularTime))
                        add(DetailRow(label = "Location", value = service.regularLocation))
                        add(DetailRow(label = "Active This Week", value = if (service.isActiveThisWeek) "Yes" else "No"))
                        if (service.specialNote.isNotBlank()) {
                            add(DetailRow(label = "Special Note", value = service.specialNote))
                        }
                    }
                )
            }
        )
        is ServiceDetailUiState.Error -> {
            // Handle error display
        }
    }
}
