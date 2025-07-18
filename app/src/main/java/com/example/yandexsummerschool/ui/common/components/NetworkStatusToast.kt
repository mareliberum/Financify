package com.example.yandexsummerschool.ui.common.components

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext

@Composable
fun NetworkStatusToast(isConnected: Boolean) {
//    val isConnected by networkObserver.isConnected.collectAsStateWithLifecycle()

    val context = LocalContext.current
    var wasConnected by remember { mutableStateOf(true) }

    LaunchedEffect(isConnected) {
        if (!isConnected && wasConnected) {
            Toast.makeText(context, "Нет подключения к интернету", Toast.LENGTH_LONG).show()
        } else if (isConnected && !wasConnected) {
            Toast.makeText(context, "Подключение восстановленно", Toast.LENGTH_LONG).show()
        }
        wasConnected = isConnected
    }
}
