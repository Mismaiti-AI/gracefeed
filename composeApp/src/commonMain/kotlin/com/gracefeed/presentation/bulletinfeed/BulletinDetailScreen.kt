package com.gracefeed.presentation.bulletinfeed

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.compose.viewmodel.koinViewModel
import com.gracefeed.core.presentation.screens.GenericDetailScreen
import com.gracefeed.core.presentation.components.DetailCard
import com.gracefeed.core.presentation.components.DetailRow
import com.gracefeed.core.presentation.components.InfoRow
import com.gracefeed.domain.model.Bulletin

@Composable
fun BulletinDetailScreen(
    bulletinId: String,
    viewModel: BulletinDetailViewModel = koinViewModel(),
    onBackClick: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    
    when (val state = uiState) {
        is BulletinDetailUiState.Loading -> {
            Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }
        is BulletinDetailUiState.Success -> {
            GenericDetailScreen(
                title = state.bulletin.title,
                item = state.bulletin,
                isLoading = false,
                onBackClick = onBackClick,
                showBack = true,
                onEditClick = null,
                onDeleteClick = null,
                menuActions = emptyList(),
                headerContent = null,
                detailContent = { bulletin ->
                    DetailCard(title = "Daily Verse", rows = listOf(
                        DetailRow(label = "Verse", value = bulletin.dailyVerse),
                        DetailRow(label = "News Content", value = bulletin.newsContent),
                        DetailRow(label = "Events", value = bulletin.events),
                        DetailRow(label = "Weekday Gatherings", value = bulletin.weekdayGatherings),
                    ))

                    
                    InfoRow(label = "Week Start Date", value = bulletin.weekStartDate.toString())
                }
            )
        }
        is BulletinDetailUiState.Error -> {
            Box(modifier = Modifier.fillMaxSize()) {
                Text(
                    text = "Error: ${state.message}",
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
    
    // Load the bulletin when the screen is composed
    LaunchedEffect(bulletinId) {
        viewModel.loadBulletin(bulletinId)
    }
}