package com.example.yandexsummerschool.ui.navigation

/**
 * Маршруты приложения. Все пути должны быть уникальными.
 */
sealed class Routes(val route: String) {
    data object ExpensesScreen : Routes("Expenses")

    data object IncomesScreen : Routes("Incomes")

    data object SettingsScreen : Routes("Settings")

    data object AccountScreen : Routes("Account")

    data object ExpenseArticleScreen : Routes("ExpenseArticles")

    data object MyHistoryScreen : Routes("history/{operationType}")

    data object EditorAccountScreen : Routes("EditorAccountScreen")
}
