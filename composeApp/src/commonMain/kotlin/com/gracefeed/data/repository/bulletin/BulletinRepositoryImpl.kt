package com.gracefeed.data.repository.bulletin

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import com.gracefeed.domain.model.Bulletin
import com.gracefeed.core.data.firestore.FirestoreService

class BulletinRepositoryImpl(private val firestore: FirestoreService) : BulletinRepository {
    private val collectionPath = "bulletins"
    private val _items = MutableStateFlow<List<Bulletin>>(emptyList())
    override val items = _items.asStateFlow()
    private val _isLoading = MutableStateFlow(false)
    override val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    private val _error = MutableStateFlow<String?>(null)
    override val error: StateFlow<String?> = _error.asStateFlow()

    override suspend fun loadAll() {
        _isLoading.value = true
        try {
            val documents = firestore.getCollection(collectionPath)
            val bulletins = documents.map { doc ->
                doc.toDomain()
            }
            _items.value = bulletins
        } catch (e: Exception) {
            _error.value = e.message
        } finally {
            _isLoading.value = false
        }
    }

    override suspend fun getById(id: String): Bulletin? {
        return try {
            val document = firestore.getDocument(collectionPath, id)
            document?.toDomain()
        } catch (e: Exception) {
            _error.value = e.message
            null
        }
    }

    override suspend fun insert(bulletin: Bulletin) {
        try {
            val data = bulletin.toMap()
            firestore.setDocument(collectionPath, bulletin.id, data)
            loadAll()
        } catch (e: Exception) {
            _error.value = e.message
        }
    }

    override suspend fun update(bulletin: Bulletin) {
        try {
            val data = bulletin.toMap()
            firestore.setDocument(collectionPath, bulletin.id, data)
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

    private fun Map<String, Any?>.toDomain(): Bulletin {
        return Bulletin(
            id = (this["id"] as? String) ?: "",
            title = (this["title"] as? String) ?: "",
            weekStartDate = (this["weekStartDate"] as? Long)?.let { kotlin.time.Instant.fromEpochMilliseconds(it) } ?: kotlin.time.Instant.DISTANT_PAST,
            dailyVerse = (this["dailyVerse"] as? String) ?: "",
            newsContent = (this["newsContent"] as? String) ?: "",
            events = (this["events"] as? String) ?: "",
            weekdayGatherings = (this["weekdayGatherings"] as? String) ?: ""
        )
    }

    private fun Bulletin.toMap(): Map<String, Any?> {
        return mapOf(
            "id" to this.id,
            "title" to this.title,
            "weekStartDate" to this.weekStartDate.toEpochMilliseconds(),
            "dailyVerse" to this.dailyVerse,
            "newsContent" to this.newsContent,
            "events" to this.events,
            "weekdayGatherings" to this.weekdayGatherings
        )
    }
}