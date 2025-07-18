package com.example.yandexsummerschool.ui.features.editTransactions.common

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.yandexsummerschool.R
import com.example.yandexsummerschool.ui.common.components.CustomErrorDialog

@Composable
fun SaveAndSendLaterDialog(
    onRetry: () -> Unit,
    onDismiss: () -> Unit,
) {
    CustomErrorDialog(
        title = stringResource(R.string.save_operation),
        message = stringResource(R.string.save_operation_message),
        onRetry = onRetry,
        onDismiss = onDismiss,
        confirmButtonText = stringResource(R.string.save),
        dismissButtonText = stringResource(R.string.dont_save),
    )
}
