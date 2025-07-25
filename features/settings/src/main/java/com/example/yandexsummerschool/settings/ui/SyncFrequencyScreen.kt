package com.example.yandexsummerschool.settings.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.yandexsummerschool.common.R
import com.example.yandexsummerschool.ui.common.components.TopAppBar

@Composable
fun SyncFrequencyScreen(
    viewModelFactory: ViewModelProvider.Factory,
    onBack: (() -> Unit)?
) {
    val viewModel: SettingsScreenViewModel = viewModel(factory = viewModelFactory)
    val syncFrequency by viewModel.getSyncFrequency.collectAsStateWithLifecycle()

    if (syncFrequency == null) return
    var sliderPosition by remember { mutableFloatStateOf(syncFrequency?.toFloat() ?: 60f) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = "Частота синхронизации",
                leadingIcon = painterResource(R.drawable.arrow_back),
                onLeadingClick = onBack,
            )
        },
    )
    { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(horizontal = 8.dp, vertical = 8.dp)
        ) {
            Text(
                modifier = Modifier.padding(bottom = 8.dp),
                text = "Частота синхронизации: ${sliderPosition.toInt()} минут"
            )
            Slider(
                value = sliderPosition,
                onValueChange = {
                    sliderPosition = it
                },
                onValueChangeFinished = {
                    viewModel.setSyncFrequency(sliderPosition.toInt())
                },
                valueRange = 30f..360f,
                steps = (360 - 30) / 30 - 1 // шаг 15 минут
            )
        }
    }
}
