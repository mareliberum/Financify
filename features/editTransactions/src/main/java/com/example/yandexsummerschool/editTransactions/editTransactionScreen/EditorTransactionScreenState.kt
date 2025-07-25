package com.example.yandexsummerschool.editTransactions.editTransactionScreen

import com.example.yandexsummerschool.ui.common.uiModels.TransactionUiModel

sealed interface EditorTransactionScreenState {
    data class Content(
        val transaction: TransactionUiModel,
    ) : EditorTransactionScreenState

    data class Error(
        val message: String,
    ) : EditorTransactionScreenState

    data object Loading : EditorTransactionScreenState
}
