package com.gracefeed.domain.usecase

import com.gracefeed.domain.model.Event
import com.gracefeed.data.repository.event.EventRepository

class ViewEventDetailsUseCase(private val repository: EventRepository) {
    suspend operator fun invoke(id: String): Event? = repository.getById(id)
}