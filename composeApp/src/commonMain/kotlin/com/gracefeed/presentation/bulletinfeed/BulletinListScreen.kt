package com.gracefeed.presentation.bulletinfeed

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.compose.viewmodel.koinViewModel
import com.gracefeed.core.presentation.components.ListItemCard
import com.gracefeed.core.presentation.screens.GenericListScreen
import com.gracefeed.domain.model.Bulletin

@Composable
fun BulletinListScreen(
    viewModel: BulletinListViewModel = koinViewModel(),
    onItemClick: (String) -> Unit = {},
    onBackClick: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    
    when (val state = uiState) {
        is BulletinListUiState.Loading -> {
            Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(modifier = Modifier.align(Center))
            }
        }
        is BulletinListUiState.Success -> {
            GenericListScreen(
                title = "Bulletin Feed",
                items = state.bulletins,
                isLoading = false,
                emptyMessage = "No bulletins available",
                onItemClick = { bulletin -> onItemClick(bulletin.id) },
                onAddClick = { /* TODO */ },
                onRefresh = { viewModel.refresh() },
                searchEnabled = false,
                showFab = false,
                itemContent = { bulletin ->
                    ListItemCard(
                        title = bulletin.title,
                        subtitle = bulletin.dailyVerse,
                    )
                }
            )
        }
        is BulletinListUiState.Error -> {
            Box(modifier = Modifier.fillMaxSize()) {
                androidx.compose.material3.Text(
                    text = "Error: ${state.message}",
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}