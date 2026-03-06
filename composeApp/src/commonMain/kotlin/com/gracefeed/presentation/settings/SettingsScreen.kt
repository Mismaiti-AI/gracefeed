package com.gracefeed.presentation.settings

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.compose.viewmodel.koinViewModel
import com.gracefeed.core.presentation.screens.GenericSettingsScreen
import com.gracefeed.core.presentation.screens.SettingsSection
import com.gracefeed.core.presentation.screens.SettingsItem
import com.gracefeed.core.presentation.components.SwitchRow

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = koinViewModel(),
    onItemClick: (String) -> Unit = {},
    onBackClick: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    when (val state = uiState) {
        is SettingsUiState.Loading -> {
            Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }
        is SettingsUiState.Success -> {
            val darkModeEnabled = state.settings.firstOrNull()?.darkModeEnabled ?: false
            
            GenericSettingsScreen(
                title = "Settings",
                showBack = false,
                showAppearanceSection = true
            )
        }
        is SettingsUiState.Error -> {
            // Error display would go here if needed
            Box(modifier = Modifier.fillMaxSize()) {
                androidx.compose.material3.Text(
                    text = "Error: ${state.message}",
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}