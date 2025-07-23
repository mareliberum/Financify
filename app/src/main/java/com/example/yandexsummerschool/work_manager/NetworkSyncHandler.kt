package com.example.yandexsummerschool.work_manager

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.work.Constraints
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager

const val ONE_TIME_SYNC_WORK = "ONE_TIME_SYNC_WORK"

@Composable
fun NetworkSyncHandler(isConnected: Boolean) {
    var wasConnected by remember { mutableStateOf(true) }
    val context = LocalContext.current

    LaunchedEffect(isConnected) {
        if (isConnected && !wasConnected) {
            val workRequest =
                OneTimeWorkRequestBuilder<SynchronizeWorkManager>()
                    .setConstraints(
                        Constraints.Builder()
                            .setRequiredNetworkType(NetworkType.CONNECTED)
                            .build(),
                    )
                    .build()
            WorkManager.getInstance(context).enqueueUniqueWork(
                ONE_TIME_SYNC_WORK,
                existingWorkPolicy = ExistingWorkPolicy.REPLACE,
                workRequest,
            )
        }
        wasConnected = isConnected
    }
}
