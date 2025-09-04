package com.example.yandexsummerschool.expenses

import com.example.yandexsummerschool.domain.models.Result
import com.example.yandexsummerschool.domain.models.TransactionDomainModel
import com.example.yandexsummerschool.domain.repositories.TransactionsRepository
import com.example.yandexsummerschool.domain.useCases.expenses.GetExpensesUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test


class GetExpensesUseCaseTest {

    val mockRepository: TransactionsRepository = mockk()
    val getExpensesUseCase = GetExpensesUseCase(mockRepository)

    @Test
    fun `invoke should return expense transactions`() {
        // Arrange
        val testId = 123
        val testStartDate = "01/01/1970"
        val testEndDate = "12/12/2025"

        // Act
        val mockAnswer: Result<List<TransactionDomainModel>> =
            Result.Success(listOf(TransactionDomainModel("1", 2, "mock", "12", "RUB", true)))
        coEvery { mockRepository.getExpenseTransactions(testId, testStartDate, testEndDate) } returns mockAnswer

        // Assert
        runBlocking {
            val actual = getExpensesUseCase(testId, testStartDate, testEndDate)
            assertEquals(actual, mockAnswer)
        }
    }


}
