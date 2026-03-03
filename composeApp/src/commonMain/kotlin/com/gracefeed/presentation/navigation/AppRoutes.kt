package com.gracefeed.presentation.navigation

import kotlinx.serialization.Serializable

object AppRoutes {
    // Tab routes (bottom nav):
    @Serializable object Dashboard
    @Serializable object Bulletin
    @Serializable object Services
    @Serializable object Events
    @Serializable object Sermons
    @Serializable object Settings
    
    // Detail routes (no bottom nav):
    @Serializable data class BulletinDetail(val id: String)
    @Serializable data class ServiceDetail(val id: String)
    @Serializable data class EventDetail(val id: String)
    @Serializable data class SermonDetail(val id: String)
}