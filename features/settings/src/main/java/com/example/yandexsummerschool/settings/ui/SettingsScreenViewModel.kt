package com.example.yandexsummerschool.settings.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.yandexsummerschool.settings.domain.colors.GetAccentColorUseCase
import com.example.yandexsummerschool.settings.domain.theme.GetDarkThemeUseCase
import com.example.yandexsummerschool.settings.domain.syncFrequency.GetFrequencyUseCase
import com.example.yandexsummerschool.settings.domain.colors.SetAccentColorUseCase
import com.example.yandexsummerschool.settings.domain.theme.SetDarkThemeUseCase
import com.example.yandexsummerschool.settings.domain.syncFrequency.SetFrequencyUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel для экрана настроек.
 */
class SettingsScreenViewModel @Inject constructor(
    private val setDarkThemeUseCase: SetDarkThemeUseCase,
    private val setAccentColorUseCase: SetAccentColorUseCase,
    private val getAccentColorUseCase: GetAccentColorUseCase,
    private val getFrequencyUseCase: GetFrequencyUseCase,
    private val setFrequencyUseCase: SetFrequencyUseCase,
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

    fun setDarkTheme(isEnabled: Boolean) {
        viewModelScope.launch {
            setDarkThemeUseCase(isEnabled)
        }
    }

    fun setAccentColor(color: Long){
        viewModelScope.launch(Dispatchers.IO) {
            setAccentColorUseCase(color)
        }
    }


    fun setSyncFrequency(frequency: Int){
        viewModelScope.launch(Dispatchers.IO) {
            setFrequencyUseCase(frequency)
        }
    }

}
