package com.gracefeed.domain.usecase

import kotlinx.coroutines.flow.StateFlow
import com.gracefeed.domain.model.Event
import com.gracefeed.data.repository.event.EventRepository

class GetEventListUseCase(private val repository: EventRepository) {
    operator fun invoke(): StateFlow<List<Event>> = repository.items
    suspend fun load() { repository.loadAll() }
}