package com.example.yandexsummerschool.settings.domain.theme

import com.example.yandexsummerschool.domain.repositories.SettingsRepository
import javax.inject.Inject

class SetDarkThemeUseCase @Inject constructor(
    private val settingsRepository: SettingsRepository,
) {

    suspend operator fun invoke(isEnabled: Boolean){
        settingsRepository.setDarkTheme(isEnabled)
    }
}
