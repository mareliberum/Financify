package com.example.yandexsummerschool.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.yandexsummerschool.domain.NetworkObserver
import com.example.yandexsummerschool.ui.common.components.NetworkStatusToast
import com.example.yandexsummerschool.ui.screens.accountScreen.account.AccountScreen
import com.example.yandexsummerschool.ui.screens.addTransactionScreen.creator.AddTransactionScreen
import com.example.yandexsummerschool.ui.screens.addTransactionScreen.editor.EditorTransactionScreen
import com.example.yandexsummerschool.ui.screens.articlesScreen.ArticlesScreen
import com.example.yandexsummerschool.ui.screens.expensesScreen.ExpensesScreen
import com.example.yandexsummerschool.ui.screens.incomesScreen.IncomesScreen
import com.example.yandexsummerschool.ui.screens.myHistoryScreen.MyHistoryScreen
import com.example.yandexsummerschool.ui.screens.myHistoryScreen.TransactionType
import com.example.yandexsummerschool.ui.screens.settings.SettingsScreen

@Composable
fun AppNavGraph(viewModelFactory: ViewModelProvider.Factory) {
    val context = LocalContext.current
    val networkObserver = remember { NetworkObserver(context) }
    DisposableEffect(Unit) {
        networkObserver.startObserving()
        onDispose {
            networkObserver.stopObserving()
        }
    }
    // TODO перенести главный скаффолд в котором будет нав бар
    NetworkStatusToast(networkObserver)
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Routes.ExpensesScreen.route) {
        composable(Routes.ExpensesScreen.route) { ExpensesScreen(viewModelFactory, navController) }
        composable(Routes.IncomesScreen.route) { IncomesScreen(navController, viewModelFactory) }
        composable(Routes.SettingsScreen.route) { SettingsScreen(navController, viewModelFactory) }
        composable(Routes.AccountScreen.route) { AccountScreen(navController, viewModelFactory) }
        composable(Routes.ExpenseArticleScreen.route) { ArticlesScreen(navController, viewModelFactory) }
        composable(
            Routes.MyHistoryScreen.route,
            arguments = listOf(navArgument("operationType") { type = NavType.StringType }),
        ) { backStackEntry ->
            val operationType = backStackEntry.arguments?.getString("operationType")
            val type = TransactionType.entries.find { it.key == operationType } ?: TransactionType.EXPENSE
            MyHistoryScreen(navController, type, viewModelFactory)
        }

        composable(
            "AddTransactionScreen?isIncome={isIncome}",
            arguments = listOf(navArgument("isIncome") { type = NavType.BoolType }),
        ) { backStackEntry ->
            val isIncome = backStackEntry.arguments?.getBoolean("isIncome") ?: false
            AddTransactionScreen(viewModelFactory, navController, isIncome)
        }

        composable(
            Routes.EditorTransactionScreen.route,
            arguments = listOf(navArgument("transactionId") { type = NavType.IntType }),
        ) { backStackEntry ->
            val transactionId = backStackEntry.arguments?.getInt("transactionId") ?: return@composable
            EditorTransactionScreen(viewModelFactory, navController, transactionId)
        }
    }
}
