package com.gracefeed.data.repository.bulletin

import kotlinx.coroutines.flow.StateFlow
import com.gracefeed.domain.model.Bulletin

interface BulletinRepository {
    val items: StateFlow<List<Bulletin>>
    val isLoading: StateFlow<Boolean>
    val error: StateFlow<String?>
    suspend fun loadAll()
    suspend fun getById(id: String): Bulletin?
    suspend fun insert(bulletin: Bulletin)
    suspend fun update(bulletin: Bulletin)
    suspend fun delete(id: String)
}