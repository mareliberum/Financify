package com.example.yandexsummerschool.domain.useCases.transactions.local.get

import com.example.yandexsummerschool.domain.models.GroupedByCategoryTransactions
import com.example.yandexsummerschool.domain.repositories.TransactionsDbRepository
import javax.inject.Inject

class GetSumByCategoryUseCase @Inject constructor(
    private val repository: TransactionsDbRepository,
) {
    suspend operator fun invoke(
        startDate: String,
        endDate: String,
        isIncome: Boolean,
    ): List<GroupedByCategoryTransactions> {
        return repository.getSumByCategory(startDate, endDate, isIncome)
    }
}
