package com.gracefeed.presentation.dashboard

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LibraryBooks
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.compose.viewmodel.koinViewModel
import com.gracefeed.core.presentation.screens.GenericDashboardScreen
import com.gracefeed.core.presentation.screens.DashboardStat
import com.gracefeed.core.presentation.screens.QuickAction
import com.gracefeed.core.presentation.components.StatCard
import com.gracefeed.core.presentation.components.SectionHeader
import com.gracefeed.core.presentation.components.ListItemCard

@Composable
fun DashboardScreen(
    viewModel: DashboardViewModel = koinViewModel(),
    onItemClick: (String) -> Unit = {},
    onBackClick: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    
    when (val state = uiState) {
        is com.gracefeed.core.presentation.UiState.Loading -> {
            Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }
        is com.gracefeed.core.presentation.UiState.Success -> {
            when (val dashboardState = state.data) {
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
                                value = dashboardState.bulletinCount.toString(),
                                icon = Icons.Default.Home
                            ) { onItemClick("bulletins") },
                            DashboardStat(
                                label = "Upcoming",
                                value = dashboardState.upcomingEvents.size.toString(),
                                icon = Icons.Default.Event
                            ) { onItemClick("events") },
                            DashboardStat(
                                label = "Sermons",
                                value = dashboardState.latestSermons.size.toString(),
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
                        recentItems = dashboardState.upcomingEvents,
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
                        androidx.compose.material3.Text(
                            text = "Error: ${dashboardState.message}",
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                }
            }
        }
        is com.gracefeed.core.presentation.UiState.Error -> {
            Box(modifier = Modifier.fillMaxSize()) {
                androidx.compose.material3.Text(
                    text = "Error: ${state.message}",
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}