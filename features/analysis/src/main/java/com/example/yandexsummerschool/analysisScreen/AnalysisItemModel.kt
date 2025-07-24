package com.example.yandexsummerschool.analysisScreen

import com.example.yandexsummerschool.chart.CategoryForPieChartUiModel
import com.example.yandexsummerschool.domain.models.GroupedByCategoryTransactions

data class AnalysisItemModel(
    val categoryId: String,
    val categoryName: String,
    val leadingEmoji: String,
    val amount: Double,
)

fun GroupedByCategoryTransactions.toAnalysisItem(): AnalysisItemModel {
    return AnalysisItemModel(
        categoryId = categoryId,
        categoryName = categoryName,
        leadingEmoji = emoji,
        amount = amount,
    )
}

fun AnalysisItemModel.toCategoryForPieChartUiModel(sum: Double): CategoryForPieChartUiModel {
    return CategoryForPieChartUiModel(
        emoji = leadingEmoji,
        name = categoryName,
        amount = amount.toString(),
        percent = (amount/sum).toFloat()
    )
}

