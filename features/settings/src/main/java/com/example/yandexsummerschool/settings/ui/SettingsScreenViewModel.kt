package com.example.yandexsummerschool.settings.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.yandexsummerschool.settings.domain.GetAccentColorUseCase
import com.example.yandexsummerschool.settings.domain.GetDarkThemeUseCase
import com.example.yandexsummerschool.settings.domain.SetAccentColorUseCase
import com.example.yandexsummerschool.settings.domain.SetDarkThemeUseCase
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

    fun setDarkTheme(isEnabled: Boolean) {
        viewModelScope.launch {
            setDarkThemeUseCase(isEnabled)
        }
    }

    fun setAccentColor(color: Long){
        viewModelScope.launch {
            setAccentColorUseCase(color)
        }
    }

}
