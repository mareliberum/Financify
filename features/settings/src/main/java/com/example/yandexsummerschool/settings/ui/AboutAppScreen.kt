package com.example.yandexsummerschool.settings.ui

import android.os.Build
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.yandexsummerschool.ui.common.components.TopAppBar
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun AboutAppScreen(onBack: () -> Unit) {
    val context = LocalContext.current
    val packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
    val versionName = packageInfo.versionName ?: "N/A"
    val versionCode = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
        packageInfo.longVersionCode.toString()
    } else {
        packageInfo.versionCode.toString()
    }
    val lastUpdateTime = packageInfo.lastUpdateTime
    val formattedDate = remember(lastUpdateTime) {
        val sdf = SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.getDefault())
        sdf.format(Date(lastUpdateTime))
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = "Информация о приложении",
                leadingIcon = painterResource(com.example.yandexsummerschool.common.R.drawable.arrow_back),
                onLeadingClick = onBack
            )
        },
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,

        ) {
            Text(
                text = stringResource(
                    com.example.yandexsummerschool.settings.R.string.app_version,
                    versionName,
                    versionCode
                ),
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = stringResource(com.example.yandexsummerschool.settings.R.string.last_update, formattedDate),
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
