package com.gracefeed.presentation.dashboard

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LibraryBooks
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.compose.viewmodel.koinViewModel
import com.gracefeed.core.presentation.screens.GenericDashboardScreen
import com.gracefeed.core.presentation.screens.DashboardStat
import com.gracefeed.core.presentation.screens.QuickAction
import com.gracefeed.core.presentation.components.ListItemCard

@Composable
fun DashboardScreen(
    viewModel: DashboardViewModel = koinViewModel(),
    onItemClick: (String) -> Unit = {},
    onBackClick: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    when (val state = uiState) {
        is DashboardUiState.Loading -> {
            Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }
        is DashboardUiState.Success -> {
            GenericDashboardScreen(
                title = "GraceFeed",
                greeting = "Welcome back!",
                stats = listOf(
                    DashboardStat(
                        label = "Bulletins",
                        value = state.bulletinCount.toString(),
                        icon = Icons.Default.Home
                    ) { onItemClick("bulletins") },
                    DashboardStat(
                        label = "Upcoming",
                        value = state.upcomingEvents.size.toString(),
                        icon = Icons.Default.Event
                    ) { onItemClick("events") },
                    DashboardStat(
                        label = "Sermons",
                        value = state.latestSermons.size.toString(),
                        icon = Icons.Default.LibraryBooks
                    ) { onItemClick("sermons") }
                ),
                quickActions = listOf(
                    QuickAction(
                        label = "Events",
                        icon = Icons.Default.Event
                    ) { onItemClick("events") },
                    QuickAction(
                        label = "Sermons",
                        icon = Icons.Default.LibraryBooks
                    ) { onItemClick("sermons") }
                ),
                isLoading = false,
                onSettingsClick = { onItemClick("settings") },
                onNotificationsClick = { onItemClick("notifications") },
                recentItems = state.upcomingEvents,
                recentTitle = "Upcoming Events",
                onSeeAllClick = { onItemClick("events") },
                onRecentItemClick = { event -> onItemClick("event_detail:${event.id}") },
                recentItemContent = { event ->
                    ListItemCard(title = event.title, subtitle = event.location)
                }
            )
        }
        is DashboardUiState.Error -> {
            Box(modifier = Modifier.fillMaxSize()) {
                Text(
                    text = "Error: ${state.message}",
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}
