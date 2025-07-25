package com.example.yandexsummerschool.settings.domain.useCases.locale

import com.example.yandexsummerschool.domain.models.AppLocale
import com.example.yandexsummerschool.domain.repositories.SettingsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLocaleUseCase @Inject constructor(
    private val settingsRepository: SettingsRepository,
){
    operator fun invoke(): Flow<AppLocale> {
        return settingsRepository.getAppLocale()
    }
}
