package com.gracefeed.domain.usecase

import com.gracefeed.domain.model.Bulletin
import com.gracefeed.data.repository.bulletin.BulletinRepository

class ViewBulletinDetailsUseCase(private val repository: BulletinRepository) {
    suspend operator fun invoke(id: String): Bulletin? = repository.getById(id)
}