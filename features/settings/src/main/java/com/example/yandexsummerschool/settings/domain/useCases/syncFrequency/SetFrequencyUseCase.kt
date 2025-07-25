package com.example.yandexsummerschool.settings.domain.useCases.syncFrequency

import com.example.yandexsummerschool.domain.repositories.SettingsRepository
import javax.inject.Inject

class SetFrequencyUseCase @Inject constructor(
    private val repo: SettingsRepository,
) {
    suspend operator fun invoke(frequency: Int) {
        repo.setSyncFrequency(frequency)
    }
}

