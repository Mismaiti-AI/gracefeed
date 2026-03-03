package com.gracefeed.data.repository.service

import kotlinx.coroutines.flow.StateFlow
import com.gracefeed.domain.model.Service

interface ServiceRepository {
    val items: StateFlow<List<Service>>
    val isLoading: StateFlow<Boolean>
    val error: StateFlow<String?>
    suspend fun loadAll()
    suspend fun getById(id: String): Service?
    suspend fun insert(service: Service)
    suspend fun update(service: Service)
    suspend fun delete(id: String)
}