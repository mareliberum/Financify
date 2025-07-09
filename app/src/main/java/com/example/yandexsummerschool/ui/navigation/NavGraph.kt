package com.example.yandexsummerschool.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.yandexsummerschool.domain.NetworkObserver
import com.example.yandexsummerschool.ui.common.components.NetworkStatusToast
import com.example.yandexsummerschool.ui.screens.accountScreen.account.AccountScreen
import com.example.yandexsummerschool.ui.screens.accountScreen.editor.EditorAccountScreen
import com.example.yandexsummerschool.ui.screens.addTransactionScreen.AddTransactionScreen
import com.example.yandexsummerschool.ui.screens.articlesScreen.ArticlesScreen
import com.example.yandexsummerschool.ui.screens.expensesScreen.ExpensesScreen
import com.example.yandexsummerschool.ui.screens.incomesScreen.IncomesScreen
import com.example.yandexsummerschool.ui.screens.myHistoryScreen.MyHistoryScreen
import com.example.yandexsummerschool.ui.screens.myHistoryScreen.TransactionType
import com.example.yandexsummerschool.ui.screens.settings.SettingsScreen

@Composable
fun AppNavGraph() {
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
        composable(Routes.ExpensesScreen.route) { ExpensesScreen(navController) }
        composable(Routes.IncomesScreen.route) { IncomesScreen(navController) }
        composable(Routes.SettingsScreen.route) { SettingsScreen(navController) }
        composable(Routes.AccountScreen.route) { AccountScreen(navController) }
        composable(Routes.ExpenseArticleScreen.route) { ArticlesScreen(navController) }
        composable(
            Routes.MyHistoryScreen.route,
            arguments = listOf(navArgument("operationType") { type = NavType.StringType }),
        ) { backStackEntry ->
            val operationType = backStackEntry.arguments?.getString("operationType")
            val type = TransactionType.entries.find { it.key == operationType } ?: TransactionType.EXPENSE
            MyHistoryScreen(navController, type)
        }

        composable(Routes.EditorAccountScreen.route) {
            EditorAccountScreen(navController)
        }

        composable(Routes.AddTransactionScreen.route) {
            AddTransactionScreen(navController)
        }


    }
}
