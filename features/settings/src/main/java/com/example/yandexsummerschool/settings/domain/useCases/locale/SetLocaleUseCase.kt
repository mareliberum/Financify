package com.example.yandexsummerschool.settings.domain.useCases.locale

import com.example.yandexsummerschool.domain.models.AppLocale
import com.example.yandexsummerschool.domain.repositories.SettingsRepository
import javax.inject.Inject

class SetLocaleUseCase @Inject constructor(
    private val settingsRepository: SettingsRepository,
) {
    suspend operator fun invoke(locale: AppLocale) {
        return settingsRepository.setAppLocale(locale)
    }
}
