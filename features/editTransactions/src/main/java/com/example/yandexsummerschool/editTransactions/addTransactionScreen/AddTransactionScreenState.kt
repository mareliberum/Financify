package com.example.yandexsummerschool.editTransactions.addTransactionScreen

import com.example.yandexsummerschool.ui.common.uiModels.TransactionUiModel

sealed interface AddTransactionScreenState {
    data class Content(
        val transaction: TransactionUiModel,
    ) : AddTransactionScreenState

    data class Error(
        val message: String,
    ) : AddTransactionScreenState

    data object Loading : AddTransactionScreenState
}
