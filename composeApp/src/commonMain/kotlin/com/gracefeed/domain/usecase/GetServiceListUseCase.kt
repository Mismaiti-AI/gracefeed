package com.gracefeed.domain.usecase

import kotlinx.coroutines.flow.StateFlow
import com.gracefeed.domain.model.Service
import com.gracefeed.data.repository.service.ServiceRepository

class GetServiceListUseCase(private val repository: ServiceRepository) {
    operator fun invoke(): StateFlow<List<Service>> = repository.items
    suspend fun load() { repository.loadAll() }
}