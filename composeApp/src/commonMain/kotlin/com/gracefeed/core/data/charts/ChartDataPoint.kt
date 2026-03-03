package com.gracefeed.core.data.charts

import androidx.compose.ui.graphics.Color

data class ChartDataPoint(
    val label: String,
    val value: Float,
    val color: Color = Color.Unspecified
)
