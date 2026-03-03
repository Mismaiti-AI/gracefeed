package com.gracefeed.presentation.sermonarchive

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.compose.viewmodel.koinViewModel
import com.gracefeed.core.presentation.screens.GenericMediaPlayerScreen
import com.gracefeed.core.presentation.screens.MediaPlayerItem
import com.gracefeed.domain.model.Sermon

@Composable
fun SermonDetailScreen(
    sermonId: String,
    viewModel: SermonDetailViewModel = koinViewModel(),
    onBackClick: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    when (val state = uiState) {
        is SermonDetailUiState.Loading -> {
            Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }
        is SermonDetailUiState.Success -> {
            val sermon = state.sermon
            val mediaPlayerItem = MediaPlayerItem(
                title = sermon.title,
                url = sermon.youtubeUrl,
                thumbnailUrl = null,
                isAudio = false
            )
            
            GenericMediaPlayerScreen(
                title = sermon.title,
                item = mediaPlayerItem,
                isLoading = false,
                onBackClick = onBackClick,
                relatedItems = emptyList(),
                onRelatedItemClick = {}
            )
        }
        is SermonDetailUiState.Error -> {
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