package com.gracefeed.data.repository.event

import kotlinx.coroutines.flow.StateFlow
import com.gracefeed.domain.model.Event

interface EventRepository {
    val items: StateFlow<List<Event>>
    val isLoading: StateFlow<Boolean>
    val error: StateFlow<String?>
    suspend fun loadAll()
    suspend fun getById(id: String): Event?
    suspend fun insert(event: Event)
    suspend fun update(event: Event)
    suspend fun delete(id: String)
}