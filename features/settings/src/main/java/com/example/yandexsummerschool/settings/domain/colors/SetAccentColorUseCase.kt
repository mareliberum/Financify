package com.example.yandexsummerschool.settings.domain.colors

import com.example.yandexsummerschool.domain.repositories.SettingsRepository
import javax.inject.Inject

class SetAccentColorUseCase@Inject constructor(
    private val settingsRepository: SettingsRepository,
) {
    suspend operator fun invoke(color: Long){
        settingsRepository.setAccentColor(color)
    }
}
