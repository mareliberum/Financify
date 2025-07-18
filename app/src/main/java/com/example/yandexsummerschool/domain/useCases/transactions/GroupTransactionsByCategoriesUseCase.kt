package com.example.yandexsummerschool.domain.useCases.transactions

import com.example.yandexsummerschool.domain.models.GroupedByCategoryTransactions
import com.example.yandexsummerschool.domain.models.TransactionDomainModel
import javax.inject.Inject

class GroupTransactionsByCategoriesUseCase @Inject constructor() {
    operator fun invoke(transactions: List<TransactionDomainModel>): List<GroupedByCategoryTransactions> {
        return transactions
            .groupBy { Triple(it.categoryId, it.categoryName, it.emoji ?: "") }
            .map { (key, group) ->
                GroupedByCategoryTransactions(
                    categoryId = key.first.toString(),
                    categoryName = key.second,
                    emoji = key.third,
                    amount = group.sumOf { it.amount.toDoubleOrNull() ?: 0.0 },
                )
            }
    }
}
