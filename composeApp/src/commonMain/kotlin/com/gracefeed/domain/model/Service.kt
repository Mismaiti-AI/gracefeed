package com.gracefeed.domain.model

data class Service(
    val id: String = "",
    val serviceName: String = "",
    val regularTime: String = "",
    val regularLocation: String = "",
    val isActiveThisWeek: Boolean = false,
    val specialNote: String = ""
)