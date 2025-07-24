package com.example.yandexsummerschool.chart

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.yandexsummerschool.ui.theme.negativeAmount
import com.example.yandexsummerschool.ui.theme.positiveAmount
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlin.math.abs

@Composable
fun ColumnChart(
    values: ImmutableList<Float>,
    labels: ImmutableList<String>,
    modifier: Modifier = Modifier,
) {
    val maxPositive = values.maxOrNull()?.coerceAtLeast(0f) ?: 0f
    val maxNegative = values.minOrNull()?.coerceAtMost(0f)?.let { -it } ?: 0f
    val maxValue = maxPositive + maxNegative

    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .height(250.dp)
            .padding(horizontal = 16.dp)
    ) {
        val totalUnits = values.size * 3
        val unitWidth = size.width / totalUnits
        val barWidth = unitWidth
        val space = unitWidth * 2
        val chartHeight = size.height - 40.dp.toPx()
        val zeroY = (maxPositive / maxValue) * chartHeight

        // Bars
        values.forEachIndexed { index, value ->
            val x = index * (barWidth + space)
            val barHeight = (abs(value) / maxValue) * chartHeight
            val y = if (value >= 0f) zeroY - barHeight else zeroY

            val barColor = if (value >= 0f) positiveAmount else negativeAmount

            drawRoundRect(
                color = barColor,
                topLeft = Offset(x, y),
                size = Size(barWidth, barHeight),
                cornerRadius = CornerRadius(barWidth / 2, barWidth / 2)
            )
        }

        // X axis line
        drawLine(
            color = Color.Black,
            start = Offset(0f, zeroY),
            end = Offset(size.width, zeroY),
            strokeWidth = 2.dp.toPx()
        )

        // X axis labels (better readable)
        val labelPaint = android.graphics.Paint().apply {
            color = android.graphics.Color.BLACK
            textAlign = android.graphics.Paint.Align.CENTER
            textSize = 28f
            isFakeBoldText = true
            isAntiAlias = true
            setShadowLayer(4f, 1f, 1f, android.graphics.Color.LTGRAY)
        }

        val nativeCanvas = drawContext.canvas.nativeCanvas
        val totalBars = values.size
        val totalWidth = totalBars * (barWidth + space)

        labels.forEachIndexed { i, label ->
            val relativeX = i.toFloat() / (labels.size - 1).coerceAtLeast(1)  // от 0.0 до 1.0
            val x = relativeX * totalWidth

            val y = chartHeight + 20.dp.toPx()

            nativeCanvas.save()
//            nativeCanvas.rotate(-45f, x, y)
            nativeCanvas.drawText(label, x, y, labelPaint)
            nativeCanvas.restore()
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun preview(){
    val data = listOf(
        10f, -25f, 12f, 18f, 35f, -15f, 10f, 45f, 22f, 8f, 0f, 5f,
    )
    val labels = listOf("янв", "июнь", "дек")

    ColumnChart(
        values = data.toImmutableList(),
        labels = labels.toImmutableList(),
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
    )
}
