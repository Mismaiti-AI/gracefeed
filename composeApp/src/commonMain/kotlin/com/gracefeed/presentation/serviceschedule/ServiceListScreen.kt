package com.gracefeed.presentation.serviceschedule

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.compose.viewmodel.koinViewModel
import com.gracefeed.core.presentation.screens.GenericListScreen
import com.gracefeed.core.presentation.components.ListItemCard
import com.gracefeed.domain.model.Service

@Composable
fun ServiceListScreen(
    viewModel: ServiceListViewModel = koinViewModel(),
    onItemClick: (String) -> Unit = {},
    onBackClick: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    when (val state = uiState) {
        is ServiceListUiState.Loading -> {
            Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(modifier = Modifier.align(Center))
            }
        }
        is ServiceListUiState.Success -> GenericListScreen(
            title = "Services",
            items = state.services,
            onItemClick = { service -> onItemClick(service.id) },
            onAddClick = { /* TODO */ },
            onRefresh = { viewModel.refresh() },
            itemContent = { service ->
                ListItemCard(title = service.serviceName, subtitle = service.regularTime)
            }
        )
        is ServiceListUiState.Error -> {
            // Handle error display
        }
    }
}