package com.gracefeed.domain.model

import kotlin.time.Instant

data class Bulletin(
    val id: String = "",
    val title: String = "",
    val weekStartDate: Instant = Instant.DISTANT_PAST,
    val dailyVerse: String = "",
    val newsContent: String = "",
    val events: String = "",
    val weekdayGatherings: String = ""
)