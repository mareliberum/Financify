package com.example.yandexsummerschool.settings.domain

import com.example.yandexsummerschool.domain.repositories.SettingsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetDarkThemeUseCase @Inject constructor(
    private val settingsRepository: SettingsRepository,
) {

    operator fun invoke(): Flow<Boolean> {
        return settingsRepository.isDarkTheme()
    }
}
