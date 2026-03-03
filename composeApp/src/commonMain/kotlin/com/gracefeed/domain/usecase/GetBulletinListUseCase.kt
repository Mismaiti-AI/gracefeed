package com.gracefeed.domain.usecase

import kotlinx.coroutines.flow.StateFlow
import com.gracefeed.domain.model.Bulletin
import com.gracefeed.data.repository.bulletin.BulletinRepository

class GetBulletinListUseCase(private val repository: BulletinRepository) {
    operator fun invoke(): StateFlow<List<Bulletin>> = repository.items
    suspend fun load() { repository.loadAll() }
}