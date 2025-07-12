package com.example.yandexsummerschool.ui.common.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun CustomErrorDialog(
    message: String,
    onRetry: () -> Unit,
    onDismiss: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Ошибка") },
        text = { Text(message) },
        confirmButton = {
            Button(onClick = onRetry) {
                Text("Повторить")
            }
        },
        dismissButton = {
            OutlinedButton(onClick = onDismiss) {
                Text("Закрыть")
            }
        },
    )
}
