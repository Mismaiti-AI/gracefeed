package com.gracefeed.data.repository.event

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import com.gracefeed.domain.model.Event
import com.gracefeed.core.data.firestore.FirestoreService

class EventRepositoryImpl(private val firestore: FirestoreService) : EventRepository {
    private val collectionPath = "events"
    private val _items = MutableStateFlow<List<Event>>(emptyList())
    override val items = _items.asStateFlow()
    private val _isLoading = MutableStateFlow(false)
    override val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    private val _error = MutableStateFlow<String?>(null)
    override val error: StateFlow<String?> = _error.asStateFlow()

    override suspend fun loadAll() {
        _isLoading.value = true
        try {
            val result = firestore.getCollection(collectionPath)
            _items.value = result.mapNotNull { it.toDomain() }
        } catch (e: Exception) {
            _error.value = e.message
        } finally {
            _isLoading.value = false
        }
    }

    override suspend fun getById(id: String): Event? {
        return try {
            val document = firestore.getDocument(collectionPath, id)
            document?.toDomain()
        } catch (e: Exception) {
            _error.value = e.message
            null
        }
    }

    override suspend fun insert(event: Event) {
        try {
            firestore.setDocument(collectionPath, event.id, event.toMap())
            loadAll()
        } catch (e: Exception) {
            _error.value = e.message
        }
    }

    override suspend fun update(event: Event) {
        try {
            firestore.setDocument(collectionPath, event.id, event.toMap())
            loadAll()
        } catch (e: Exception) {
            _error.value = e.message
        }
    }

    override suspend fun delete(id: String) {
        try {
            firestore.deleteDocument(collectionPath, id)
            loadAll()
        } catch (e: Exception) {
            _error.value = e.message
        }
    }

    private fun Map<String, Any?>.toDomain(): Event? {
        return Event(
            id = (this["id"] as? String) ?: return null,
            title = this["title"] as? String ?: "",
            startDate = (this["startDate"] as? Long)?.let { kotlin.time.Instant.fromEpochMilliseconds(it) } ?: kotlin.time.Instant.DISTANT_PAST,
            endDate = (this["endDate"] as? Long)?.let { kotlin.time.Instant.fromEpochMilliseconds(it) } ?: kotlin.time.Instant.DISTANT_PAST,
            isRecurring = this["isRecurring"] as? Boolean ?: false,
            recurrencePattern = this["recurrencePattern"] as? String ?: "",
            description = this["description"] as? String ?: "",
            location = this["location"] as? String ?: "",
            category = this["category"] as? String ?: "",
            hasRsvp = this["hasRsvp"] as? Boolean ?: false,
            rsvpLink = this["rsvpLink"] as? String ?: ""
        )
    }

    private fun Event.toMap(): Map<String, Any?> {
        return mapOf(
            "id" to id,
            "title" to title,
            "startDate" to startDate.toEpochMilliseconds(),
            "endDate" to endDate.toEpochMilliseconds(),
            "isRecurring" to isRecurring,
            "recurrencePattern" to recurrencePattern,
            "description" to description,
            "location" to location,
            "category" to category,
            "hasRsvp" to hasRsvp,
            "rsvpLink" to rsvpLink
        )
    }
}