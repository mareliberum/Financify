package com.example.yandexsummerschool.domain.useCases.account

import com.example.yandexsummerschool.domain.models.Result
import com.example.yandexsummerschool.domain.repositories.TransactionsRepository
import com.example.yandexsummerschool.domain.utils.date.getStartAndCurrentDayOfYearRange
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject

/**
 * Возвращает список из разниц между доходами и расходами
 * для каждого из месяцев с начала текущего года
 */
class GetStatisticsForChart @Inject constructor(
    private val repository: TransactionsRepository,
) {
    suspend operator fun invoke(accountId: Int): Result<List<Float>> {
        val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val range = getStartAndCurrentDayOfYearRange()
        val start = range.first
        val end = range.second
        return when (val result = repository.getTransactions(accountId, start, end)) {
            is Result.Success -> {
                val transactions = result.data
                // Группируем по номеру месяца (1–12)
                val groupedByMonth = transactions.groupBy { transaction ->
                    val date = formatter.parse(transaction.date)
                    val cal = Calendar.getInstance().apply { time = date }
                    cal.get(Calendar.MONTH)
                }

                val monthlyDiffs = (0..11).map { month ->
                    val monthTransactions = groupedByMonth[month].orEmpty()
                    val income = monthTransactions
                        .filter { it.isIncome }
                        .fold(0f) { acc, tx -> acc + (tx.amount.toFloatOrNull() ?: 0f) }
                    val expense = monthTransactions
                        .filter { !it.isIncome }
                        .fold(0f) { acc, tx -> acc + (tx.amount.toFloatOrNull() ?: 0f) }

                    income - expense
                }
                Result.Success(monthlyDiffs)
            }
            is Result.Failure -> Result.Failure(result.exception)

        }
    }
}
