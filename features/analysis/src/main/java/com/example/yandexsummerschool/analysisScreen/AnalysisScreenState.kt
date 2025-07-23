package com.example.yandexsummerschool.analysisScreen

sealed interface AnalysisScreenState {
    data class Content(
	    val items: List<AnalysisItemModel>,
	    val sum: Double,
    ) : AnalysisScreenState

    data class Error(
        val message: String,
    ) : AnalysisScreenState

    data object Loading : AnalysisScreenState

    data object Empty : AnalysisScreenState
}
