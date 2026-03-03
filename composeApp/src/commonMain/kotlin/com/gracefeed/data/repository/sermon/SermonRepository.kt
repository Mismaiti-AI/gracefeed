package com.gracefeed.data.repository.sermon

import kotlinx.coroutines.flow.StateFlow
import com.gracefeed.domain.model.Sermon

interface SermonRepository {
    val items: StateFlow<List<Sermon>>
    val isLoading: StateFlow<Boolean>
    val error: StateFlow<String?>
    suspend fun loadAll()
    suspend fun getById(id: String): Sermon?
    suspend fun insert(sermon: Sermon)
    suspend fun update(sermon: Sermon)
    suspend fun delete(id: String)
}