package com.example.yandexsummerschool.settings.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.yandexsummerschool.domain.models.AppLocale
import com.example.yandexsummerschool.ui.common.components.TopAppBar

@Composable
fun LanguagePickerScreen(
    viewModelFactory: ViewModelProvider.Factory,
    onBack: () -> Unit
) {
    val viewModel: SettingsScreenViewModel = viewModel(factory = viewModelFactory)
    val currentLocale by viewModel.appLocale.collectAsStateWithLifecycle()
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = "Язык приложения",
                leadingIcon = painterResource(com.example.yandexsummerschool.common.R.drawable.arrow_back),
                onLeadingClick = onBack,
            )
        }
    ) { padding ->
        Column(
            Modifier
                .padding(padding)
                .padding(16.dp)) {
            Text("Выберите язык", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(16.dp))
            LanguageOption(
                label = "Русский",
                selected = currentLocale == AppLocale.RUSSIAN
            ) {
                viewModel.setLocale(AppLocale.RUSSIAN, context)
            }
            LanguageOption(
                label = "English",
                selected = currentLocale == AppLocale.ENGLISH
            ) {
                viewModel.setLocale(AppLocale.ENGLISH, context)
            }
        }
    }
}

@Composable
fun LanguageOption(label: String, selected: Boolean, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(selected = selected, onClick = onClick)
        Spacer(modifier = Modifier.width(8.dp))
        Text(label)
    }
}
