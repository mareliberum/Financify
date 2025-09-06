package com.example.yandexsummerschool.repositories.transactions

import com.example.yandexsummerschool.data.dto.transactions.Account
import com.example.yandexsummerschool.data.dto.transactions.Category
import com.example.yandexsummerschool.data.dto.transactions.PostTransactionResponseDto
import com.example.yandexsummerschool.data.dto.transactions.TransactionDto
import com.example.yandexsummerschool.data.dto.transactions.TransactionRequestDto
import com.example.yandexsummerschool.data.local.room.dao.TransactionsDao
import com.example.yandexsummerschool.data.local.room.entities.TransactionEntity
import com.example.yandexsummerschool.data.repositories.transactions.TransactionsRepositoryImpl
import com.example.yandexsummerschool.data.retrofit.ShmrFinanceApi
import com.example.yandexsummerschool.domain.models.Result
import com.example.yandexsummerschool.domain.models.TransactionDomainModel
import com.example.yandexsummerschool.domain.models.UpdatedTransactionDomainModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class TransactionsRepositoryImplTest {

    private lateinit var repository: TransactionsRepositoryImpl
    private lateinit var mockApi: ShmrFinanceApi
    private lateinit var mockDao: TransactionsDao

    @Before
    fun setUp() {
        mockApi = mockk()
        mockDao = mockk()
        repository = TransactionsRepositoryImpl(mockApi, mockDao)
    }

    @Test
    fun `getTransactions with successful API response should return success and save to database`() = runTest {
        // Given
        val accountId = 123
        val startDate = "2024-01-01"
        val endDate = "2024-01-31"

        val transactionDto = TransactionDto(
            id = 1,
            account = Account(123, "Test Account", "1000.0", "RUB"),
            category = Category(1, "Food", "🍕", false),
            amount = "100.50",
            transactionDate = "2024-01-15",
            comment = "Test transaction",
            createdAt = "2024-01-15T10:00:00Z",
            updatedAt = "2024-01-15T10:00:00Z"
        )

        val apiResponse = Response.success(listOf(transactionDto))

        coEvery { mockApi.getTransactions(accountId, startDate, endDate) } returns apiResponse
        coEvery { mockDao.insertAllTransactions(any()) } returns Unit
        coEvery { mockDao.getAllTransactions(any(), any()) } returns emptyList()

        // When
        val result = repository.getTransactions(accountId, startDate, endDate)

        // Then
        assertTrue(result is Result.Success)
        val successResult = result as Result.Success
        assertEquals(1, successResult.data.size)
        assertEquals("1", successResult.data[0].id)
        assertEquals("Food", successResult.data[0].categoryName)
        assertEquals("100.50", successResult.data[0].amount)

        coVerify { mockDao.insertAllTransactions(any()) }
    }

    @Test
    fun `getTransactions with exception and no cached data should return failure`() = runTest {
        // Given
        val accountId = 123
        val startDate = null
        val endDate = null

        coEvery { mockApi.getTransactions(accountId, startDate, endDate) } throws Exception("Network error")

        // When
        val result = repository.getTransactions(accountId, startDate, endDate)

        // Then
        assertTrue(result is Result.Failure)
        val failureResult = result as Result.Failure
        assertEquals("Network error", failureResult.exception.message)
    }

    @Test
    fun `getIncomeTransactions should filter income transactions only`() = runTest {
        // Given
        val accountId = 123
        val startDate = "2024-01-01"
        val endDate = "2024-01-31"

        val incomeTransaction = TransactionDto(
            id = 1,
            account = Account(123, "Test Account", "1000.0", "RUB"),
            category = Category(1, "Salary", "💰", true),
            amount = "50000.00",
            transactionDate = "2024-01-15",
            comment = "Salary",
            createdAt = "2024-01-15T10:00:00Z",
            updatedAt = "2024-01-15T10:00:00Z"
        )

        val expenseTransaction = TransactionDto(
            id = 2,
            account = Account(123, "Test Account", "1000.0", "RUB"),
            category = Category(2, "Food", "🍕", false),
            amount = "100.50",
            transactionDate = "2024-01-15",
            comment = "Food",
            createdAt = "2024-01-15T10:00:00Z",
            updatedAt = "2024-01-15T10:00:00Z"
        )
        val dbResponse = TransactionEntity(
            id = 2,
            categoryId = 42,
            categoryName = "Food",
            amount = "100.50",
            date = "2024-01-15",
            comment = "Food",
            currency = "RUB",
            lastSyncDate = "2024-01-15T10:00:00Z",
            isIncome = true,

            )

        val apiResponse = Response.success(listOf(incomeTransaction, expenseTransaction))

        coEvery { mockApi.getTransactions(accountId, startDate, endDate) } returns apiResponse
        coEvery { mockDao.insertAllTransactions(any()) } returns Unit
        coEvery { mockDao.getAllTransactions(any(), any()) } returns listOf(dbResponse)

        // When
        val result = repository.getIncomeTransactions(accountId, startDate, endDate)

        // Then
        assertTrue(result is Result.Success)
        val successResult = result as Result.Success
        assertEquals(1, successResult.data.size)
        assertTrue(successResult.data[0].isIncome)
        assertEquals("Salary", successResult.data[0].categoryName)
    }

    @Test
    fun `getExpenseTransactions should filter expense transactions only`() = runTest {
        // Given
        val accountId = 123
        val startDate = "2024-01-01"
        val endDate = "2024-01-31"

        val incomeTransaction = TransactionDto(
            id = 1,
            account = Account(123, "Test Account", "1000.0", "RUB"),
            category = Category(1, "Salary", "💰", true),
            amount = "50000.00",
            transactionDate = "2024-01-15",
            comment = "Salary",
            createdAt = "2024-01-15T10:00:00Z",
            updatedAt = "2024-01-15T10:00:00Z"
        )

        val expenseTransaction = TransactionDto(
            id = 2,
            account = Account(123, "Test Account", "1000.0", "RUB"),
            category = Category(2, "Food", "🍕", false),
            amount = "100.50",
            transactionDate = "2024-01-15",
            comment = "Food",
            createdAt = "2024-01-15T10:00:00Z",
            updatedAt = "2024-01-15T10:00:00Z"
        )

        val apiResponse = Response.success(listOf(incomeTransaction, expenseTransaction))

        coEvery { mockApi.getTransactions(accountId, startDate, endDate) } returns apiResponse
        coEvery { mockDao.insertAllTransactions(any()) } returns Unit
        coEvery { mockDao.getAllTransactions(any(), any()) } returns emptyList()

        // When
        val result = repository.getExpenseTransactions(accountId, startDate, endDate)

        // Then
        assertTrue(result is Result.Success)
        val successResult = result as Result.Success
        assertEquals(1, successResult.data.size)
        assertFalse(successResult.data[0].isIncome)
        assertEquals("Food", successResult.data[0].categoryName)
    }

    @Test
    fun `postTransaction with successful response should return success`() = runTest {
        // Given
        val transaction = TransactionDomainModel(
            id = "1",
            categoryId = 1,
            categoryName = "Food",
            amount = "100.50",
            currency = "RUB",
            isIncome = false,
            emoji = "🍕",
            comment = "Test transaction",
            date = "2024-01-15"
        )

        val requestDto = TransactionRequestDto(
            accountId = 1,
            categoryId = 1,
            amount = "100.50",
            transactionDate = "2024-01-15",
            comment = "Test transaction"
        )

        val responseDto = PostTransactionResponseDto(
            id = 1,
            accountId = 1,
            categoryId = 1,
            amount = "100.50",
            transactionDate = "2024-01-15",
            comment = "Test transaction",
            cratedAt = "2024-01-15T10:00:00Z",
            updatedAt = "2024-01-15T10:00:00Z"
        )

        val apiResponse = Response.success(responseDto)

        coEvery { mockApi.postTransaction(any()) } returns apiResponse

        // When
        val result = repository.postTransaction(transaction)

        // Then
        assertTrue(result is Result.Success)
        val successResult = result as Result.Success
        assertEquals(transaction, successResult.data)
    }

    @Test
    fun `updateTransaction with successful response should return success`() = runTest {
        // Given
        val updatedTransaction = UpdatedTransactionDomainModel(
            transactionId = 1,
            accountId = 123,
            categoryId = 1,
            amount = "150.75",
            date = "2024-01-15",
            comment = "Updated transaction"
        )

        val transactionDto = TransactionDto(
            id = 1,
            account = Account(123, "Test Account", "1000.0", "RUB"),
            category = Category(1, "Food", "🍕", false),
            amount = "150.75",
            transactionDate = "2024-01-15",
            comment = "Updated transaction",
            createdAt = "2024-01-15T10:00:00Z",
            updatedAt = "2024-01-15T10:00:00Z"
        )

        val apiResponse = Response.success(transactionDto)

        coEvery { mockApi.updateTransaction(1, any()) } returns apiResponse

        // When
        val result = repository.updateTransaction(updatedTransaction)

        // Then
        assertTrue(result is Result.Success)
        val successResult = result as Result.Success
        assertEquals("1", successResult.data.id)
        assertEquals("150.75", successResult.data.amount)
    }

    @Test
    fun `updateTransaction with empty response body should return failure`() = runTest {
        // Given
        val updatedTransaction = UpdatedTransactionDomainModel(
            transactionId = 1,
            accountId = 123,
            categoryId = 1,
            amount = "150.75",
            date = "2024-01-15",
            comment = "Updated transaction"
        )

        val apiResponse = Response.success<TransactionDto>(null)

        coEvery { mockApi.updateTransaction(1, any()) } returns apiResponse

        // When
        val result = repository.updateTransaction(updatedTransaction)

        // Then
        assertTrue(result is Result.Failure)
        val failureResult = result as Result.Failure
        assertEquals("Server error - empty response body", failureResult.exception.message)
    }

    @Test
    fun `deleteTransactionById with successful response should return success`() = runTest {
        // Given
        val transactionId = 1
        val apiResponse = Response.success<Unit>(Unit)

        coEvery { mockApi.deleteTransactionById(transactionId) } returns apiResponse

        // When
        val result = repository.deleteTransactionById(transactionId)

        // Then
        assertTrue(result is Result.Success)
        assertEquals(Unit, (result as Result.Success).data)
    }

    @Test
    fun `getTransactionById with successful response should return success and save to database`() = runTest {
        // Given
        val transactionId = 1
        val transactionDto = TransactionDto(
            id = 1,
            account = Account(123, "Test Account", "1000.0", "RUB"),
            category = Category(1, "Food", "🍕", false),
            amount = "100.50",
            transactionDate = "2024-01-15",
            comment = "Test transaction",
            createdAt = "2024-01-15T10:00:00Z",
            updatedAt = "2024-01-15T10:00:00Z"
        )

        val apiResponse = Response.success(transactionDto)

        coEvery { mockApi.getTransaction(transactionId) } returns apiResponse
        coEvery { mockDao.insertTransaction(any()) } returns Unit

        // When
        val result = repository.getTransactionById(transactionId)

        // Then
        assertTrue(result is Result.Success)
        val successResult = result as Result.Success
        assertEquals("1", successResult.data.id)
        assertEquals("Food", successResult.data.categoryName)
        assertEquals("100.50", successResult.data.amount)

        coVerify { mockDao.insertTransaction(any()) }
    }


    @Test
    fun `getTransactionById with exception and no cached data should return failure`() = runTest {
        // Given
        val transactionId = 1

        coEvery { mockApi.getTransaction(transactionId) } throws Exception("Network error")
        coEvery { mockDao.getTransactionById(transactionId) } returns null

        // When
        val result = repository.getTransactionById(transactionId)

        // Then
        assertTrue(result is Result.Failure)
        val failureResult = result as Result.Failure
        assertEquals("Network error", failureResult.exception.message)
    }
}
