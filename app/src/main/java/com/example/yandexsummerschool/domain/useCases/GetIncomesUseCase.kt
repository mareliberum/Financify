package com.example.yandexsummerschool.domain.useCases

import com.example.yandexsummerschool.data.repositories.ShmrFinanceRepository
import com.example.yandexsummerschool.domain.dto.Result
import com.example.yandexsummerschool.domain.models.TransactionModel
import javax.inject.Inject

class GetIncomesUseCase @Inject constructor(
	private val repository: ShmrFinanceRepository
) {
	suspend operator fun invoke(accountId:Int, startDate: String? = null, endDate: String? = null): Result<List<TransactionModel>> {
		return try {
			repository.getIncomeTransactions(accountId, startDate, endDate)
		}catch (e:Exception){
			Result.Failure(e)
		}
	}
}