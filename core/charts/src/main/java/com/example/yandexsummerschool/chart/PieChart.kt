package com.example.yandexsummerschool.chart

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

@Composable
fun PieChart(
    pieChartData: ImmutableList<CategoryForPieChartUiModel>,
    modifier: Modifier = Modifier,
    strokeWidth: Float = 34f,
) {
    val colors = PieChartColors
    val data = pieChartData.sortedByDescending { it.percent }
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            var startAngle = -90f
            data.forEachIndexed { i, category ->
                val sweepAngle = category.percent * 360f
                drawArc(
                    color = colors[i],
                    startAngle = startAngle,
                    sweepAngle = sweepAngle,
                    useCenter = false,
                    style = Stroke(width = strokeWidth),
                    size = Size(size.width, size.height),
                    topLeft = Offset.Zero
                )
                startAngle += sweepAngle
            }
        }
        //TODO delete
//        val innerBoxSize = with(LocalDensity.current) {
//            (150.dp - strokeWidth.dp * 2)
//        }
        Box(
            modifier = modifier,
            contentAlignment = Alignment.Center
        ) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(2.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
	                .padding(strokeWidth.dp)
            ) {
                items(data){ category ->
                    val label = "${(category.percent * 100).toInt()}% ${category.name}"
                    Text(
                        text = label,
                        style = MaterialTheme.typography.bodySmall,
                        maxLines = 1,
                        softWrap = false,
                        overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun PreviewPieChartWithLegend() {
    val mockCategories = listOf(
        CategoryForPieChartUiModel(
            emoji = "🏠",
            name = "Аренда квартиры",
            amount = "25000",
            percent = 0.3f,
        ),
        CategoryForPieChartUiModel(
            emoji = "👗",
            name = "Одежда",
            amount = "4500",
            percent = 0.2f,
        ),
        CategoryForPieChartUiModel(
            emoji = "🐶",
            name = "На собачку",
            amount = "3200",
            percent = 0.4f,
        ),
        CategoryForPieChartUiModel(
            emoji = "🛠",
            name = "Ремонт квартиры",
            amount = "18000",
            percent = 0.05f,
        ),
        CategoryForPieChartUiModel(
            emoji = "🍭",
            name = "Продукты",
            amount = "7000",
            percent = 0.025f,
        ),
        CategoryForPieChartUiModel(
            emoji = "🏋️",
            name = "Спортзал",
            amount = "2500",
            percent = 0.025f,
        ),
    )

    PieChart(
        pieChartData = mockCategories.toImmutableList(),
        modifier = Modifier.size(200.dp)
    )
}

private val PieChartColors = listOf(
    Color(0xFF26EA7F),
    Color(0xFFFFEB3B),
    Color(0xFFFF5722),
    Color(0xFF3F51B5),
    Color(0xFFE91E63),
    Color(0xFF9C27B0),
    Color(0xFF00BCD4),
    Color(0xFF795548),
    Color(0xFF4CAF50),
    Color(0xFFFF9800),
    Color(0xFF2196F3),
    Color(0xFF607D8B),
    Color(0xFFCDDC39),
    Color(0xFF009688),
    Color(0xFF8BC34A),
    Color(0xFF673AB7),
    Color(0xFF03A9F4),
    Color(0xFFFFC107),
    Color(0xFFBA68C8),
    Color(0xFFA1887F),
    Color(0xFF90A4AE),
    Color(0xFF80CBC4),
    Color(0xFFB2DFDB),
    Color(0xFFFFAB91),
    Color(0xFFFFCC80),
    Color(0xFFE6EE9C),
    Color(0xFFCE93D8),
    Color(0xFFB0BEC5),
    Color(0xFFD7CCC8),
    Color(0xFFFFF59D)
)

