package com.gracefeed.data.repository.service

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import com.gracefeed.domain.model.Service
import com.gracefeed.core.data.firestore.FirestoreService

class ServiceRepositoryImpl(private val firestore: FirestoreService) : ServiceRepository {
    private val collectionPath = "services"
    private val _items = MutableStateFlow<List<Service>>(emptyList())
    override val items = _items.asStateFlow()
    private val _isLoading = MutableStateFlow(false)
    override val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    private val _error = MutableStateFlow<String?>(null)
    override val error: StateFlow<String?> = _error.asStateFlow()

    override suspend fun loadAll() {
        _isLoading.value = true
        try {
            val documents = firestore.getCollection(collectionPath)
            _items.value = documents.mapNotNull { it.toDomain() }
        } catch (e: Exception) {
            _error.value = e.message
        } finally {
            _isLoading.value = false
        }
    }

    override suspend fun getById(id: String): Service? {
        return try {
            val document = firestore.getDocument(collectionPath, id)
            document?.toDomain()
        } catch (e: Exception) {
            _error.value = e.message
            null
        }
    }

    override suspend fun insert(service: Service) {
        try {
            firestore.setDocument(collectionPath, service.id, service.toMap())
            loadAll()
        } catch (e: Exception) {
            _error.value = e.message
        }
    }

    override suspend fun update(service: Service) {
        try {
            firestore.setDocument(collectionPath, service.id, service.toMap())
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

    private fun Map<String, Any?>.toDomain(): Service? {
        return Service(
            id = this["id"] as? String ?: return null,
            serviceName = this["serviceName"] as? String ?: "",
            regularTime = this["regularTime"] as? String ?: "",
            regularLocation = this["regularLocation"] as? String ?: "",
            isActiveThisWeek = this["isActiveThisWeek"] as? Boolean ?: false,
            specialNote = this["specialNote"] as? String ?: ""
        )
    }

    private fun Service.toMap(): Map<String, Any?> {
        return mapOf(
            "id" to this.id,
            "serviceName" to this.serviceName,
            "regularTime" to this.regularTime,
            "regularLocation" to this.regularLocation,
            "isActiveThisWeek" to this.isActiveThisWeek,
            "specialNote" to this.specialNote
        )
    }
}