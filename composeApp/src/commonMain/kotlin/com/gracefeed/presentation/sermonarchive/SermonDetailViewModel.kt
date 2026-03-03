package com.gracefeed.presentation.sermonarchive

import com.gracefeed.core.presentation.BaseViewModel
import com.gracefeed.domain.model.Sermon
import com.gracefeed.domain.usecase.ViewSermonDetailsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

sealed interface SermonDetailUiState {
    data object Loading : SermonDetailUiState
    data class Success(val sermon: Sermon) : SermonDetailUiState
    data class Error(val message: String) : SermonDetailUiState
}

class SermonDetailViewModel(
    private val viewSermonDetailsUseCase: ViewSermonDetailsUseCase
) : BaseViewModel() {
    private val _uiState = MutableStateFlow<SermonDetailUiState>(SermonDetailUiState.Loading)
    val uiState: StateFlow<SermonDetailUiState> = _uiState.asStateFlow()

    fun loadSermon(id: String) = safeLaunch {
        _uiState.value = SermonDetailUiState.Loading
        val sermon = viewSermonDetailsUseCase(id)
        _uiState.value = if (sermon != null) {
            SermonDetailUiState.Success(sermon)
        } else {
            SermonDetailUiState.Error("Sermon not found")
        }
    }
}