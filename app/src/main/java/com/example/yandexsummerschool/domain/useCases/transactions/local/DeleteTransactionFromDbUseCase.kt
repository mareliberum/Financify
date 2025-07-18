package com.example.yandexsummerschool.domain.useCases.transactions.local

import com.example.yandexsummerschool.domain.repositories.TransactionsDbRepository
import javax.inject.Inject

class DeleteTransactionFromDbUseCase @Inject constructor(
    private val repository: TransactionsDbRepository
){
    suspend operator fun invoke(id: Int){
        repository.deleteTransactionById(id)
    }
}
