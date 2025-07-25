package com.example.yandexsummerschool.settings.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.yandexsummerschool.domain.models.AppLocale
import com.example.yandexsummerschool.settings.domain.useCases.colors.GetAccentColorUseCase
import com.example.yandexsummerschool.settings.domain.useCases.colors.SetAccentColorUseCase
import com.example.yandexsummerschool.settings.domain.useCases.locale.GetLocaleUseCase
import com.example.yandexsummerschool.settings.domain.useCases.locale.SetLocaleUseCase
import com.example.yandexsummerschool.settings.domain.useCases.syncFrequency.GetFrequencyUseCase
import com.example.yandexsummerschool.settings.domain.useCases.syncFrequency.SetFrequencyUseCase
import com.example.yandexsummerschool.settings.domain.useCases.theme.GetDarkThemeUseCase
import com.example.yandexsummerschool.settings.domain.useCases.theme.SetDarkThemeUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel для экрана настроек.
 */
@Suppress("LongParameterList")
class SettingsScreenViewModel @Inject constructor(
    private val setDarkThemeUseCase: SetDarkThemeUseCase,
    private val setAccentColorUseCase: SetAccentColorUseCase,
    private val getAccentColorUseCase: GetAccentColorUseCase,
    private val getFrequencyUseCase: GetFrequencyUseCase,
    private val setFrequencyUseCase: SetFrequencyUseCase,
    private val getLocaleUseCase: GetLocaleUseCase,
    private val setLocaleUseCase: SetLocaleUseCase,
    getDarkThemeUseCase: GetDarkThemeUseCase,
) : ViewModel() {
    val isDarkTheme: StateFlow<Boolean?> = getDarkThemeUseCase().stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        null
    )
    val accentColor: StateFlow<Long?> = getAccentColorUseCase().stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        null,
    )
    val getSyncFrequency: StateFlow<Int?> = getFrequencyUseCase().stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        null,
    )
    val appLocale: StateFlow<AppLocale?> = getLocaleUseCase().stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        null,
    )

    fun setDarkTheme(isEnabled: Boolean) {
        viewModelScope.launch {
            setDarkThemeUseCase(isEnabled)
        }
    }

    fun setAccentColor(color: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            setAccentColorUseCase(color)
        }
    }

    fun setSyncFrequency(frequency: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            setFrequencyUseCase(frequency)
        }
    }

    fun setLocale(locale: AppLocale) {
        viewModelScope.launch(Dispatchers.IO) {
            setLocaleUseCase(locale)
        }
    }
}
