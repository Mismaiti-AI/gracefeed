package com.gracefeed.domain.model

import kotlin.time.Instant

data class Event(
    val id: String = "",
    val title: String = "",
    val startDate: Instant = Instant.DISTANT_PAST,
    val endDate: Instant = Instant.DISTANT_PAST,
    val isRecurring: Boolean = false,
    val recurrencePattern: String = "",
    val description: String = "",
    val location: String = "",
    val category: String = "",
    val hasRsvp: Boolean = false,
    val rsvpLink: String = ""
)