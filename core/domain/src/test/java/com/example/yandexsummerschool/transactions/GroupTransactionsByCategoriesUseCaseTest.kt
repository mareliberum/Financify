package com.example.yandexsummerschool.transactions

import com.example.yandexsummerschool.domain.models.TransactionDomainModel
import com.example.yandexsummerschool.domain.useCases.transactions.GroupTransactionsByCategoriesUseCase
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class GroupTransactionsByCategoriesUseCaseTest {

    private lateinit var useCase: GroupTransactionsByCategoriesUseCase

    @Before
    fun setUp() {
        useCase = GroupTransactionsByCategoriesUseCase()
    }

    @Test
    fun `invoke with empty list should return empty list`() {
        // Given
        val emptyTransactions = emptyList<TransactionDomainModel>()

        // When
        val result = useCase(emptyTransactions)

        // Then
        assertTrue(result.isEmpty())
    }

    @Test
    fun `invoke with single transaction should return single grouped transaction`() {
        // Given
        val transaction = TransactionDomainModel(
            id = "1",
            categoryId = 1,
            categoryName = "Food",
            amount = "100.50",
            currency = "RUB",
            isIncome = false,
            emoji = "🍕"
        )
        val transactions = listOf(transaction)

        // When
        val result = useCase(transactions)

        // Then
        assertEquals(1, result.size)
        val grouped = result[0]
        assertEquals("1", grouped.categoryId)
        assertEquals("Food", grouped.categoryName)
        assertEquals("🍕", grouped.emoji)
        assertEquals(100.50, grouped.amount, 0.01)
    }

    @Test
    fun `invoke with multiple transactions of same category should group and sum amounts`() {
        // Given
        val transactions = listOf(
            TransactionDomainModel(
                id = "1",
                categoryId = 1,
                categoryName = "Food",
                amount = "100.50",
                currency = "RUB",
                isIncome = false,
                emoji = "🍕"
            ),
            TransactionDomainModel(
                id = "2",
                categoryId = 1,
                categoryName = "Food",
                amount = "50.25",
                currency = "RUB",
                isIncome = false,
                emoji = "🍕"
            )
        )

        // When
        val result = useCase(transactions)

        // Then
        assertEquals(1, result.size)
        val grouped = result[0]
        assertEquals("1", grouped.categoryId)
        assertEquals("Food", grouped.categoryName)
        assertEquals("🍕", grouped.emoji)
        assertEquals(150.75, grouped.amount, 0.01)
    }

    @Test
    fun `invoke with transactions of different categories should return separate groups`() {
        // Given
        val transactions = listOf(
            TransactionDomainModel(
                id = "1",
                categoryId = 1,
                categoryName = "Food",
                amount = "100.50",
                currency = "RUB",
                isIncome = false,
                emoji = "🍕"
            ),
            TransactionDomainModel(
                id = "2",
                categoryId = 2,
                categoryName = "Transport",
                amount = "50.00",
                currency = "RUB",
                isIncome = false,
                emoji = "🚗"
            )
        )

        // When
        val result = useCase(transactions)

        // Then
        assertEquals(2, result.size)

        val foodGroup = result.find { it.categoryId == "1" }
        assertNotNull(foodGroup)
        assertEquals("Food", foodGroup!!.categoryName)
        assertEquals("🍕", foodGroup.emoji)
        assertEquals(100.50, foodGroup.amount, 0.01)

        val transportGroup = result.find { it.categoryId == "2" }
        assertNotNull(transportGroup)
        assertEquals("Transport", transportGroup!!.categoryName)
        assertEquals("🚗", transportGroup.emoji)
        assertEquals(50.00, transportGroup.amount, 0.01)
    }

    @Test
    fun `invoke with transaction having null emoji should use empty string`() {
        // Given
        val transaction = TransactionDomainModel(
            id = "1",
            categoryId = 1,
            categoryName = "Food",
            amount = "100.50",
            currency = "RUB",
            isIncome = false,
            emoji = null
        )
        val transactions = listOf(transaction)

        // When
        val result = useCase(transactions)

        // Then
        assertEquals(1, result.size)
        val grouped = result[0]
        assertEquals("", grouped.emoji)
    }

    @Test
    fun `invoke with transaction having invalid amount should treat as zero`() {
        // Given
        val transaction = TransactionDomainModel(
            id = "1",
            categoryId = 1,
            categoryName = "Food",
            amount = "invalid_amount",
            currency = "RUB",
            isIncome = false,
            emoji = "🍕"
        )
        val transactions = listOf(transaction)

        // When
        val result = useCase(transactions)

        // Then
        assertEquals(1, result.size)
        val grouped = result[0]
        assertEquals(0.0, grouped.amount, 0.01)
    }

    @Test
    fun `invoke with mixed valid and invalid amounts should sum correctly`() {
        // Given
        val transactions = listOf(
            TransactionDomainModel(
                id = "1",
                categoryId = 1,
                categoryName = "Food",
                amount = "100.50",
                currency = "RUB",
                isIncome = false,
                emoji = "🍕"
            ),
            TransactionDomainModel(
                id = "2",
                categoryId = 1,
                categoryName = "Food",
                amount = "invalid",
                currency = "RUB",
                isIncome = false,
                emoji = "🍕"
            ),
            TransactionDomainModel(
                id = "3",
                categoryId = 1,
                categoryName = "Food",
                amount = "25.75",
                currency = "RUB",
                isIncome = false,
                emoji = "🍕"
            )
        )

        // When
        val result = useCase(transactions)

        // Then
        assertEquals(1, result.size)
        val grouped = result[0]
        assertEquals(126.25, grouped.amount, 0.01) // 100.50 + 0.0 + 25.75
    }

    @Test
    fun `invoke should handle transactions with same categoryId but different names separately`() {
        // Given
        val transactions = listOf(
            TransactionDomainModel(
                id = "1",
                categoryId = 1,
                categoryName = "Food",
                amount = "100.50",
                currency = "RUB",
                isIncome = false,
                emoji = "🍕"
            ),
            TransactionDomainModel(
                id = "2",
                categoryId = 1,
                categoryName = "Drinks", // Different name
                amount = "50.00",
                currency = "RUB",
                isIncome = false,
                emoji = "🍕"
            )
        )

        // When
        val result = useCase(transactions)

        // Then
        assertEquals(2, result.size) // Should be treated as different categories
    }
}
