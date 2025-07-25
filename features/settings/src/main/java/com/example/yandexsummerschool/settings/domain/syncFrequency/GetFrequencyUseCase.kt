package com.example.yandexsummerschool.settings.domain.syncFrequency

import com.example.yandexsummerschool.domain.repositories.SettingsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFrequencyUseCase @Inject constructor(
    private val repo: SettingsRepository
) {
    operator fun invoke(): Flow<Int> = repo.getSyncFrequencyFlow()
}
