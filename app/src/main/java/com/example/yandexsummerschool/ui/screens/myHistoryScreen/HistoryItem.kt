package com.example.yandexsummerschool.ui.screens.myHistoryScreen

data class HistoryItem(
    val lead: String,
    val title: String,
    val sum: String,
    val currency: String,
    val time: String,
    val subtitle: String? = null,
)
