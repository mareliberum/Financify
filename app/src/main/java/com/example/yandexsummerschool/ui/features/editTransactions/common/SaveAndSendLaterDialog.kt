package com.example.yandexsummerschool.ui.features.editTransactions.common

import androidx.compose.runtime.Composable
import com.example.yandexsummerschool.ui.common.components.CustomErrorDialog

@Composable
fun SaveAndSendLaterDialog(
    onRetry: () -> Unit,
    onDismiss: () -> Unit,
) {
    CustomErrorDialog(
        message = "Вы можете сохранить операцию. Она автоматически отправится позднее.",
        onRetry = onRetry,
        onDismiss = onDismiss,
        confirmButtonText = "Сохранить",
        dismissButtonText = "Не сохранять",
    )
}
