package com.gracefeed.domain.usecase

import kotlinx.coroutines.flow.StateFlow
import com.gracefeed.domain.model.Sermon
import com.gracefeed.data.repository.sermon.SermonRepository

class GetSermonListUseCase(private val repository: SermonRepository) {
    operator fun invoke(): StateFlow<List<Sermon>> = repository.items
    suspend fun load() { repository.loadAll() }
}