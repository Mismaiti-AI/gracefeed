package com.gracefeed.domain.model

import kotlin.time.Instant

data class Sermon(
    val id: String = "",
    val title: String = "",
    val date: Instant = Instant.DISTANT_PAST,
    val serviceName: String = "",
    val speaker: String = "",
    val youtubeUrl: String = "",
    val biblePassage: String = ""
)