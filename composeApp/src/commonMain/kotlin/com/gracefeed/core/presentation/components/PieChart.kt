package com.gracefeed.core.presentation.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import com.gracefeed.core.data.charts.ChartDataPoint

@Composable
fun PieChart(
    data: List<ChartDataPoint>,
    modifier: Modifier = Modifier,
    isDonut: Boolean = false,
    showLegend: Boolean = false
) {
    val total = data.sumOf { it.value.toDouble() }.toFloat()
    val defaultColors = listOf(
        Color(0xFF2196F3), Color(0xFF4CAF50), Color(0xFFFF9800),
        Color(0xFF9C27B0), Color(0xFF00BCD4), Color(0xFF607D8B)
    )

    Column(modifier = modifier.fillMaxWidth().padding(top = 8.dp)) {
        Canvas(
            modifier = Modifier
                .size(160.dp)
                .align(Alignment.CenterHorizontally)
        ) {
            var startAngle = -90f
            data.forEachIndexed { index, point ->
                val sweep = if (total > 0) (point.value / total) * 360f else 0f
                val color = if (point.color != Color.Unspecified) point.color
                    else defaultColors[index % defaultColors.size]
                if (isDonut) {
                    drawArc(
                        color = color,
                        startAngle = startAngle,
                        sweepAngle = sweep,
                        useCenter = false,
                        style = Stroke(width = 32.dp.toPx()),
                        topLeft = Offset(16.dp.toPx(), 16.dp.toPx()),
                        size = Size(size.width - 32.dp.toPx(), size.height - 32.dp.toPx())
                    )
                } else {
                    drawArc(
                        color = color,
                        startAngle = startAngle,
                        sweepAngle = sweep,
                        useCenter = true
                    )
                }
                startAngle += sweep
            }
        }

        if (showLegend) {
            Spacer(modifier = Modifier.height(12.dp))
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                data.forEachIndexed { index, point ->
                    val color = if (point.color != Color.Unspecified) point.color
                        else defaultColors[index % defaultColors.size]
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Canvas(modifier = Modifier.size(10.dp)) {
                            drawCircle(color = color)
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = point.label,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }
        }
    }
}
