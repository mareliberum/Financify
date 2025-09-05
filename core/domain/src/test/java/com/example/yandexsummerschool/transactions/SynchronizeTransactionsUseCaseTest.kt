package com.example.yandexsummerschool.transactions

import com.example.yandexsummerschool.domain.models.Result
import com.example.yandexsummerschool.domain.models.TransactionDomainModel
import com.example.yandexsummerschool.domain.repositories.TransactionsRepository
import com.example.yandexsummerschool.domain.useCases.transactions.SynchronizeTransactionsUseCase
import com.example.yandexsummerschool.domain.utils.date.millsToIsoDateSimple
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.io.IOException
import java.net.UnknownHostException

class SynchronizeTransactionsUseCaseTest {

    private lateinit var useCase: SynchronizeTransactionsUseCase
    private lateinit var mockRepository: TransactionsRepository

    @Before
    fun setUp() {
        mockRepository = mockk()
        useCase = SynchronizeTransactionsUseCase(mockRepository)
    }

    @Test
    fun `invoke with successful repository call should return success result`() = runTest {
        // Given
        val accountId = 42
        val mockTransactions = listOf(
            TransactionDomainModel(
                id = "1",
                categoryId = 1,
                categoryName = "Food",
                amount = "100.50",
                currency = "RUB",
                isIncome = false,
                emoji = "🍕"
            )
        )

        coEvery {
            mockRepository.getTransactions(
                accountId = accountId,
                startDate = any(),
                endDate = any()
            )
        } returns Result.Success(mockTransactions)

        // When
        val result = useCase(accountId)

        // Then
        assertTrue(result is Result.Success)
        assertEquals(true, (result as Result.Success).data)
    }

    @Test
    fun `invoke with repository failure should return failure result`() = runTest {
        // Given
        val accountId = 42
        val exception = IOException("Network error")

        coEvery {
            mockRepository.getTransactions(
                accountId = accountId,
                startDate = any(),
                endDate = any()
            )
        } returns Result.Failure(exception)

        // When
        val result = useCase(accountId)

        // Then
        assertTrue(result is Result.Failure)
        assertEquals(exception, (result as Result.Failure).exception)
    }

    @Test
    fun `invoke with different exception types should propagate correctly`() = runTest {
        // Given
        val accountId = 42
        val exception = UnknownHostException("No internet connection")

        coEvery {
            mockRepository.getTransactions(
                accountId = accountId,
                startDate = any(),
                endDate = any()
            )
        } returns Result.Failure(exception)

        // When
        val result = useCase(accountId)

        // Then
        assertTrue(result is Result.Failure)
        assertEquals(exception, (result as Result.Failure).exception)
        assertTrue(result.exception is UnknownHostException)
    }

    @Test
    fun `invoke should call repository with correct date range`() = runTest {
        // Given
        val accountId = 42
        val mockTransactions = emptyList<TransactionDomainModel>()

        coEvery {
            mockRepository.getTransactions(
                accountId = accountId,
                startDate = not("1970-01-01"),
                endDate = not(millsToIsoDateSimple(System.currentTimeMillis()))
            )
        } returns Result.Failure(Exception("wrong dates"))
        coEvery {
            mockRepository.getTransactions(
                accountId = accountId,
                startDate = millsToIsoDateSimple(0L),
                endDate = millsToIsoDateSimple(System.currentTimeMillis())// current time
            )
        } returns Result.Success(mockTransactions)

        // When
        val result = useCase(accountId)

        // Then
        assertTrue(result is Result.Success)
    }

    @Test
    fun `invoke with empty transaction list should still return success`() = runTest {
        // Given
        val accountId = 42
        val emptyTransactions = emptyList<TransactionDomainModel>()

        coEvery {
            mockRepository.getTransactions(
                accountId = accountId,
                startDate = any(),
                endDate = any()
            )
        } returns Result.Success(emptyTransactions)

        // When
        val result = useCase(accountId)

        // Then
        assertTrue(result is Result.Success)
        assertEquals(true, (result as Result.Success).data)
    }

    @Test
    fun `invoke with large transaction list should return success`() = runTest {
        // Given
        val accountId = 555
        val largeTransactionList = (1..1000).map { index ->
            TransactionDomainModel(
                id = index.toString(),
                categoryId = index % 10,
                categoryName = "Category $index",
                amount = (index * 10.5).toString(),
                currency = "RUB",
                isIncome = index % 2 == 0,
                emoji = "💰"
            )
        }

        coEvery {
            mockRepository.getTransactions(
                accountId = accountId,
                startDate = any(),
                endDate = any()
            )
        } returns Result.Success(largeTransactionList)

        // When
        val result = useCase(accountId)

        // Then
        assertTrue(result is Result.Success)
        assertEquals(true, (result as Result.Success).data)
    }

    @Test
    fun `invoke with different account IDs should work correctly`() = runTest {
        // Given
        val accountIds = listOf(1, 100, 9999, -1, 0)
        val mockTransactions = listOf(
            TransactionDomainModel(
                id = "1",
                categoryId = 1,
                categoryName = "Test",
                amount = "100.0",
                currency = "RUB",
                isIncome = false
            )
        )

        accountIds.forEach { accountId ->
            coEvery {
                mockRepository.getTransactions(
                    accountId = accountId,
                    startDate = any(),
                    endDate = any()
                )
            } returns Result.Success(mockTransactions)
        }

        // When & Then
        accountIds.forEach { accountId ->
            val result = useCase(accountId)
            assertTrue("Failed for account ID: $accountId", result is Result.Success)
            assertEquals(true, (result as Result.Success).data)
        }
    }

    @Test
    fun `invoke should handle repository timeout exception`() = runTest {
        // Given
        val accountId = 123
        val timeoutException = java.net.SocketTimeoutException("Connection timeout")

        coEvery {
            mockRepository.getTransactions(
                accountId = accountId,
                startDate = any(),
                endDate = any()
            )
        } returns Result.Failure(timeoutException)

        // When
        val result = useCase(accountId)

        // Then
        assertTrue(result is Result.Failure)
        assertEquals(timeoutException, (result as Result.Failure).exception)
        assertTrue((result as Result.Failure).exception is java.net.SocketTimeoutException)
    }
}
