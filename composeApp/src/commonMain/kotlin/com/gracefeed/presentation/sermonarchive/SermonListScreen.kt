package com.gracefeed.presentation.sermonarchive

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.compose.viewmodel.koinViewModel
import com.gracefeed.core.presentation.screens.GenericListScreen
import com.gracefeed.core.presentation.components.ListItemCard
import com.gracefeed.domain.model.Sermon

@Composable
fun SermonListScreen(
    viewModel: SermonListViewModel = koinViewModel(),
    onItemClick: (String) -> Unit = {},
    onBackClick: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    when (val state = uiState) {
        is SermonListUiState.Loading -> {
            Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }
        is SermonListUiState.Success -> {
            GenericListScreen(
                title = "Sermon Archive",
                items = state.sermons,
                onItemClick = { sermon -> onItemClick(sermon.id) },
                onAddClick = { /* No add functionality for sermons */ },
                onRefresh = { viewModel.refresh() },
                searchEnabled = false,
                showFab = false,
                itemContent = { sermon ->
                    ListItemCard(
                        title = sermon.title,
                        subtitle = "${sermon.speaker} • ${sermon.serviceName}",
                        trailingContent = {
                            Icon(
                                imageVector = Icons.Default.PlayArrow,
                                contentDescription = "Play sermon"
                            )
                        }
                    )
                }
            )
        }
        is SermonListUiState.Error -> {
            // ErrorContent would be defined elsewhere
            Box(modifier = Modifier.fillMaxSize()) {
                androidx.compose.material3.Text(
                    text = "Error: ${state.message}",
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}