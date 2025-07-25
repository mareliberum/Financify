package com.example.yandexsummerschool.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.yandexsummerschool.settings.domain.GetDarkThemeUseCase
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
    getDarkThemeUseCase: GetDarkThemeUseCase,
) : ViewModel() {

    val isDarkTheme: StateFlow<Boolean?> = getDarkThemeUseCase().stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        null
    )

    fun setDarkTheme(isEnabled: Boolean) {
        viewModelScope.launch {
            setDarkThemeUseCase(isEnabled)
        }
    }

}
