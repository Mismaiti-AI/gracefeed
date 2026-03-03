package com.gracefeed.domain.usecase

import com.gracefeed.domain.model.Service
import com.gracefeed.data.repository.service.ServiceRepository

class ViewServiceDetailsUseCase(private val repository: ServiceRepository) {
    suspend operator fun invoke(id: String): Service? = repository.getById(id)
}