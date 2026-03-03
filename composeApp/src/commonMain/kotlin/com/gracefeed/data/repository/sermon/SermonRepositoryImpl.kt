package com.gracefeed.data.repository.sermon

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import com.gracefeed.domain.model.Sermon
import com.gracefeed.core.data.firestore.FirestoreService

class SermonRepositoryImpl(private val firestore: FirestoreService) : SermonRepository {
    private val collectionPath = "sermons"
    private val _items = MutableStateFlow<List<Sermon>>(emptyList())
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

    override suspend fun getById(id: String): Sermon? {
        return try {
            val document = firestore.getDocument(collectionPath, id)
            document?.toDomain()
        } catch (e: Exception) {
            _error.value = e.message
            null
        }
    }

    override suspend fun insert(sermon: Sermon) {
        try {
            firestore.setDocument(collectionPath, sermon.id, sermon.toMap())
            loadAll()
        } catch (e: Exception) {
            _error.value = e.message
        }
    }

    override suspend fun update(sermon: Sermon) {
        try {
            firestore.setDocument(collectionPath, sermon.id, sermon.toMap())
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

    private fun Map<String, Any?>.toDomain(): Sermon? {
        val id = this["id"] as? String ?: return null
        val title = this["title"] as? String ?: ""
        val dateString = this["date"] as? String
        val date = dateString?.let { 
            try {
                kotlin.time.Instant.parse(it)
            } catch (e: Exception) {
                null
            }
        } ?: kotlin.time.Instant.DISTANT_PAST
        val serviceName = this["serviceName"] as? String ?: ""
        val speaker = this["speaker"] as? String ?: ""
        val youtubeUrl = this["youtubeUrl"] as? String ?: ""
        val biblePassage = this["biblePassage"] as? String ?: ""

        return Sermon(
            id = id,
            title = title,
            date = date,
            serviceName = serviceName,
            speaker = speaker,
            youtubeUrl = youtubeUrl,
            biblePassage = biblePassage
        )
    }

    private fun Sermon.toMap(): Map<String, Any?> {
        return mapOf(
            "id" to this.id,
            "title" to this.title,
            "date" to this.date.toString(),
            "serviceName" to this.serviceName,
            "speaker" to this.speaker,
            "youtubeUrl" to this.youtubeUrl,
            "biblePassage" to this.biblePassage
        )
    }
}