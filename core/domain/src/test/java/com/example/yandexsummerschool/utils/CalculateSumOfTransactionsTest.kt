package com.example.yandexsummerschool.utils

import com.example.yandexsummerschool.domain.models.TransactionDomainModel
import com.example.yandexsummerschool.domain.utils.calculateSum
import org.junit.Assert.assertEquals
import org.junit.Test

class CalculateSumOfTransactionsTest {

    @Test
    fun `calculateSum with empty list should return zero`() {
        // Given
        val emptyTransactions = emptyList<TransactionDomainModel>()

        // When
        val result = calculateSum(emptyTransactions)

        // Then
        assertEquals("0.0", result)
    }

    @Test
    fun `calculateSum with single transaction should return correct sum`() {
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
        val result = calculateSum(transactions)

        // Then
        assertEquals("100.5", result)
    }

    @Test
    fun `calculateSum with multiple transactions should return correct sum`() {
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
                amount = "50.25",
                currency = "RUB",
                isIncome = false,
                emoji = "🚗"
            ),
            TransactionDomainModel(
                id = "3",
                categoryId = 3,
                categoryName = "Entertainment",
                amount = "75.75",
                currency = "RUB",
                isIncome = false,
                emoji = "🎬"
            )
        )

        // When
        val result = calculateSum(transactions)

        // Then
        assertEquals("226.5", result) // 100.50 + 50.25 + 75.75
    }

    @Test
    fun `calculateSum with negative amounts should handle correctly`() {
        // Given
        val transactions = listOf(
            TransactionDomainModel(
                id = "1",
                categoryId = 1,
                categoryName = "Income",
                amount = "1000.00",
                currency = "RUB",
                isIncome = true,
                emoji = "💰"
            ),
            TransactionDomainModel(
                id = "2",
                categoryId = 2,
                categoryName = "Expense",
                amount = "-500.50",
                currency = "RUB",
                isIncome = false,
                emoji = "💸"
            )
        )

        // When
        val result = calculateSum(transactions)

        // Then
        assertEquals("499.5", result) // 1000.00 + (-500.50)
    }

    @Test
    fun `calculateSum with zero amounts should include them`() {
        // Given
        val transactions = listOf(
            TransactionDomainModel(
                id = "1",
                categoryId = 1,
                categoryName = "Food",
                amount = "100.00",
                currency = "RUB",
                isIncome = false,
                emoji = "🍕"
            ),
            TransactionDomainModel(
                id = "2",
                categoryId = 2,
                categoryName = "Transport",
                amount = "0.00",
                currency = "RUB",
                isIncome = false,
                emoji = "🚗"
            ),
            TransactionDomainModel(
                id = "3",
                categoryId = 3,
                categoryName = "Entertainment",
                amount = "50.00",
                currency = "RUB",
                isIncome = false,
                emoji = "🎬"
            )
        )

        // When
        val result = calculateSum(transactions)

        // Then
        assertEquals("150.0", result) // 100.00 + 0.00 + 50.00
    }

    @Test
    fun `calculateSum with large numbers should handle correctly`() {
        // Given
        val transactions = listOf(
            TransactionDomainModel(
                id = "1",
                categoryId = 1,
                categoryName = "Large Amount",
                amount = "999999.99",
                currency = "RUB",
                isIncome = false,
                emoji = "💰"
            ),
            TransactionDomainModel(
                id = "2",
                categoryId = 2,
                categoryName = "Another Large",
                amount = "1000000.01",
                currency = "RUB",
                isIncome = false,
                emoji = "💸"
            )
        )

        // When
        val result = calculateSum(transactions)

        // Then
        assertEquals("2000000.0", result) // 999999.99 + 1000000.01
    }

    @Test
    fun `calculateSum with decimal precision should maintain accuracy`() {
        // Given
        val transactions = listOf(
            TransactionDomainModel(
                id = "1",
                categoryId = 1,
                categoryName = "Precise",
                amount = "0.01",
                currency = "RUB",
                isIncome = false,
                emoji = "💰"
            ),
            TransactionDomainModel(
                id = "2",
                categoryId = 2,
                categoryName = "Another Precise",
                amount = "0.02",
                currency = "RUB",
                isIncome = false,
                emoji = "💸"
            ),
            TransactionDomainModel(
                id = "3",
                categoryId = 3,
                categoryName = "Third Precise",
                amount = "0.03",
                currency = "RUB",
                isIncome = false,
                emoji = "🎯"
            )
        )

        // When
        val result = calculateSum(transactions)

        // Then
        assertEquals("0.06", result) // 0.01 + 0.02 + 0.03
    }

    @Test
    fun `calculateSum with mixed positive and negative should calculate correctly`() {
        // Given
        val transactions = listOf(
            TransactionDomainModel(
                id = "1",
                categoryId = 1,
                categoryName = "Positive",
                amount = "100.00",
                currency = "RUB",
                isIncome = true,
                emoji = "💰"
            ),
            TransactionDomainModel(
                id = "2",
                categoryId = 2,
                categoryName = "Negative",
                amount = "-25.50",
                currency = "RUB",
                isIncome = false,
                emoji = "💸"
            ),
            TransactionDomainModel(
                id = "3",
                categoryId = 3,
                categoryName = "Another Positive",
                amount = "75.25",
                currency = "RUB",
                isIncome = true,
                emoji = "💰"
            ),
            TransactionDomainModel(
                id = "4",
                categoryId = 4,
                categoryName = "Another Negative",
                amount = "-10.00",
                currency = "RUB",
                isIncome = false,
                emoji = "💸"
            )
        )

        // When
        val result = calculateSum(transactions)

        // Then
        assertEquals("139.75", result) // 100.00 + (-25.50) + 75.25 + (-10.00)
    }

    @Test
    fun `calculateSum with same amounts should multiply correctly`() {
        // Given
        val transactions = listOf(
            TransactionDomainModel(
                id = "1",
                categoryId = 1,
                categoryName = "Same",
                amount = "10.00",
                currency = "RUB",
                isIncome = false,
                emoji = "💰"
            ),
            TransactionDomainModel(
                id = "2",
                categoryId = 1,
                categoryName = "Same",
                amount = "10.00",
                currency = "RUB",
                isIncome = false,
                emoji = "💰"
            ),
            TransactionDomainModel(
                id = "3",
                categoryId = 1,
                categoryName = "Same",
                amount = "10.00",
                currency = "RUB",
                isIncome = false,
                emoji = "💰"
            )
        )

        // When
        val result = calculateSum(transactions)

        // Then
        assertEquals("30.0", result) // 10.00 * 3
    }

    @Test
    fun `calculateSum with integer amounts should work correctly`() {
        // Given
        val transactions = listOf(
            TransactionDomainModel(
                id = "1",
                categoryId = 1,
                categoryName = "Integer",
                amount = "100",
                currency = "RUB",
                isIncome = false,
                emoji = "💰"
            ),
            TransactionDomainModel(
                id = "2",
                categoryId = 2,
                categoryName = "Another Integer",
                amount = "200",
                currency = "RUB",
                isIncome = false,
                emoji = "💸"
            )
        )

        // When
        val result = calculateSum(transactions)

        // Then
        assertEquals("300.0", result) // 100 + 200
    }

    @Test(expected = NumberFormatException::class)
    fun `calculateSum with invalid amount should throw NumberFormatException`() {
        // Given
        val transaction = TransactionDomainModel(
            id = "1",
            categoryId = 1,
            categoryName = "Invalid",
            amount = "not_a_number",
            currency = "RUB",
            isIncome = false,
            emoji = "💰"
        )
        val transactions = listOf(transaction)

        // When
        calculateSum(transactions)

        // Then - should throw NumberFormatException
    }

    @Test(expected = NumberFormatException::class)
    fun `calculateSum with empty amount string should throw NumberFormatException`() {
        // Given
        val transaction = TransactionDomainModel(
            id = "1",
            categoryId = 1,
            categoryName = "Empty",
            amount = "",
            currency = "RUB",
            isIncome = false,
            emoji = "💰"
        )
        val transactions = listOf(transaction)

        // When
        calculateSum(transactions)

        // Then - should throw NumberFormatException
    }

    @Test(expected = NumberFormatException::class)
    fun `calculateSum with null amount should throw NumberFormatException`() {
        // Given
        val transaction = TransactionDomainModel(
            id = "1",
            categoryId = 1,
            categoryName = "Null",
            amount = "null",
            currency = "RUB",
            isIncome = false,
            emoji = "💰"
        )
        val transactions = listOf(transaction)

        // When
        calculateSum(transactions)

        // Then - should throw NumberFormatException
    }
}