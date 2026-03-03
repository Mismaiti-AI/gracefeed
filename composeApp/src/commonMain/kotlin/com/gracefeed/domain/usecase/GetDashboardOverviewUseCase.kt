package com.gracefeed.domain.usecase

import com.gracefeed.domain.model.Bulletin
import com.gracefeed.domain.model.Event
import com.gracefeed.domain.model.Sermon
import com.gracefeed.domain.model.Service
import com.gracefeed.data.repository.bulletin.BulletinRepository
import com.gracefeed.data.repository.event.EventRepository
import com.gracefeed.data.repository.sermon.SermonRepository
import com.gracefeed.data.repository.service.ServiceRepository
import kotlinx.coroutines.flow.combine
import kotlin.time.Clock

data class DashboardOverview(
    val bulletinCount: Int,
    val upcomingEvents: List<Event>,
    val latestSermons: List<Sermon>,
    val activeServices: List<Service>
)

class GetDashboardOverviewUseCase(
    private val bulletinRepository: BulletinRepository,
    private val eventRepository: EventRepository,
    private val sermonRepository: SermonRepository,
    private val serviceRepository: ServiceRepository
) {
    operator fun invoke() = combine(
        bulletinRepository.items,
        eventRepository.items,
        sermonRepository.items,
        serviceRepository.items
    ) { bulletins, events, sermons, services ->
        val now = Clock.System.now()
        val upcomingEvents = events.filter { it.startDate >= now }
        val latestSermons = sermons.sortedByDescending { it.date }.take(3)
        val activeServices = services.filter { it.isActiveThisWeek }
        
        DashboardOverview(
            bulletinCount = bulletins.size,
            upcomingEvents = upcomingEvents.take(3),
            latestSermons = latestSermons,
            activeServices = activeServices
        )
    }
    
    suspend fun load() {
        bulletinRepository.loadAll()
        eventRepository.loadAll()
        sermonRepository.loadAll()
        serviceRepository.loadAll()
    }
}