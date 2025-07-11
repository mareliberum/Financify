package com.example.yandexsummerschool.domain.useCases.transactions

import com.example.yandexsummerschool.domain.models.Result
import com.example.yandexsummerschool.domain.models.TransactionDomainModel
import com.example.yandexsummerschool.domain.repositories.TransactionsRepository
import javax.inject.Inject

class GetTransactionByIdUseCase @Inject constructor(
    private val transactionsRepository: TransactionsRepository,
) {
    suspend operator fun invoke(id: Int): Result<TransactionDomainModel> {
        return transactionsRepository.getTransactionById(id)
    }
}
