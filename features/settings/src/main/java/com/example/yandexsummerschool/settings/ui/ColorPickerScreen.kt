package com.example.yandexsummerschool.settings.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.yandexsummerschool.common.R
import com.example.yandexsummerschool.ui.common.components.TopAppBar
import com.example.yandexsummerschool.ui.theme.GreenPrimary
import com.example.yandexsummerschool.ui.theme.blue
import com.example.yandexsummerschool.ui.theme.orange
import com.example.yandexsummerschool.ui.theme.purple
import com.example.yandexsummerschool.ui.theme.red

@Composable
fun ColorPickerScreen(
    viewModelFactory: ViewModelProvider.Factory,
    onBack: () -> Unit
) {
    val viewModel: SettingsScreenViewModel = viewModel(factory = viewModelFactory)

    val selectedColor = viewModel.accentColor.collectAsStateWithLifecycle()

    val colors = listOf(
        GreenPrimary, // GreenPrimary
        blue,
        orange,
        purple,
        red,
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = "Выбор цвета",
                leadingIcon = painterResource(R.drawable.arrow_back),
                onLeadingClick = onBack,
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            colors.forEach { color ->
                val isSelected = selectedColor.value == color.value.toLong()
                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .clip(CircleShape)
                        .background(color)
                        .border(
                            width = if (isSelected) 4.dp else 2.dp,
                            color = if (isSelected) Color.Black else Color.Gray,
                            shape = CircleShape
                        )
                        .clickable {
                            viewModel.setAccentColor(color.value.toLong())
                        }
                )
            }
        }
    }
}
