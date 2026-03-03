package com.gracefeed.presentation.sermonarchive

import androidx.lifecycle.viewModelScope
import com.gracefeed.core.presentation.BaseViewModel
import com.gracefeed.core.presentation.UiState
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.combine
import com.gracefeed.domain.model.Sermon
import com.gracefeed.domain.usecase.GetSermonListUseCase

sealed interface SermonListUiState {
    data object Loading : SermonListUiState
    data class Success(val sermons: List<Sermon>) : SermonListUiState
    data class Error(val message: String) : SermonListUiState
}

class SermonListViewModel(
    private val getSermonListUseCase: GetSermonListUseCase
) : BaseViewModel() {
    val uiState: StateFlow<SermonListUiState> = combine(
        getSermonListUseCase(),
        isLoading,
        error
    ) { sermons, loading, err ->
        when {
            err != null -> SermonListUiState.Error(err)
            loading -> SermonListUiState.Loading
            else -> SermonListUiState.Success(sermons = sermons)
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), SermonListUiState.Loading)

    init { loadSermons() }
    fun loadSermons() = safeLaunch { getSermonListUseCase.load() }
    fun refresh() = safeLaunch { getSermonListUseCase.load() }
}