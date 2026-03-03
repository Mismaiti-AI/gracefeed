package com.gracefeed.domain.usecase

import com.gracefeed.domain.model.Sermon
import com.gracefeed.data.repository.sermon.SermonRepository

class ViewSermonDetailsUseCase(private val repository: SermonRepository) {
    suspend operator fun invoke(id: String): Sermon? = repository.getById(id)
}