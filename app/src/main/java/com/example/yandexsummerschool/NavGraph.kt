package com.example.yandexsummerschool

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.yandexsummerschool.accountScreen.account.AccountScreen
import com.example.yandexsummerschool.accountScreen.editor.EditorAccountScreen
import com.example.yandexsummerschool.analysisScreen.AnalysisScreen
import com.example.yandexsummerschool.analysisScreen.di.AnalysisDiProvider
import com.example.yandexsummerschool.articlesScreen.ArticlesScreen
import com.example.yandexsummerschool.articlesScreen.di.ArticlesDiProvider
import com.example.yandexsummerschool.editTransactions.addTransactionScreen.AddTransactionScreen
import com.example.yandexsummerschool.editTransactions.editTransactionScreen.EditorTransactionScreen
import com.example.yandexsummerschool.expensesScreen.ExpensesScreen
import com.example.yandexsummerschool.expensesScreen.di.ExpensesDiProvider
import com.example.yandexsummerschool.incomesScreen.IncomesScreen
import com.example.yandexsummerschool.incomesScreen.di.IncomesDiProvider
import com.example.yandexsummerschool.myHistoryScreen.MyHistoryScreen
import com.example.yandexsummerschool.settings.navigation.settingsNavGraph
import com.example.yandexsummerschool.ui.NetworkObserver
import com.example.yandexsummerschool.ui.common.TransactionType
import com.example.yandexsummerschool.ui.common.components.NetworkStatusToast
import com.example.yandexsummerschool.ui.navigation.Routes
import com.example.yandexsummerschool.work_manager.NetworkSyncHandler

@Composable
fun AppNavGraph(viewModelFactory: ViewModelProvider.Factory) {
    val context = LocalContext.current
    val appComponent = context.appComponent
    val networkObserver = remember { NetworkObserver(context) }
    val isConnected by networkObserver.isConnected.collectAsStateWithLifecycle()

    DisposableEffect(Unit) {
        networkObserver.startObserving()
        onDispose {
            networkObserver.stopObserving()
        }
    }
    NetworkSyncHandler(isConnected)
    NetworkStatusToast(isConnected)
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Routes.ExpensesScreen.route) {
        composable(Routes.ExpensesScreen.route) {
            val factory = remember { ExpensesDiProvider.provideFactory(appComponent) }
            ExpensesScreen(factory, navController)
        }

        composable(Routes.IncomesScreen.route) {
            val incomesViewModelFactory = remember { IncomesDiProvider.provideFactory(appComponent) }
            IncomesScreen(navController = navController, viewModelFactory = incomesViewModelFactory)
        }

        settingsNavGraph(navController)

        composable(Routes.AccountScreen.route) { AccountScreen(navController, viewModelFactory) }
        composable(Routes.ExpenseArticleScreen.route) {
            val articlesViewModelFactory = remember { ArticlesDiProvider.provideFactory(appComponent) }
            ArticlesScreen(navController = navController, viewModelFactory = articlesViewModelFactory)
        }
        composable(
            Routes.MyHistoryScreen.route,
            arguments = listOf(navArgument("operationType") { type = NavType.StringType }),
        ) { backStackEntry ->
            val operationType = backStackEntry.arguments?.getString("operationType")
            val type = TransactionType.entries.find { it.key == operationType } ?: TransactionType.EXPENSE
            MyHistoryScreen(navController, type, viewModelFactory)
        }

        composable(Routes.EditorAccountScreen.route) {
            EditorAccountScreen(navController, viewModelFactory)
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
            arguments =
                listOf(
                    navArgument("transactionId") { type = NavType.IntType },
                    navArgument("isIncome") { type = NavType.BoolType },
                ),
        ) { backStackEntry ->
            val transactionId = backStackEntry.arguments?.getInt("transactionId") ?: return@composable
            val isIncome = backStackEntry.arguments?.getBoolean("isIncome") ?: false
            EditorTransactionScreen(viewModelFactory, navController, transactionId, isIncome)
        }

        composable(
            Routes.AnalysisScreen.route,
            arguments = listOf(navArgument("isIncome") { type = NavType.BoolType }),
        ) { backStackEntry ->
            val isIncome = backStackEntry.arguments?.getBoolean("isIncome") ?: false
            val analysisViewModelFactory = AnalysisDiProvider.provideFactory(appComponent)
            AnalysisScreen(navController, isIncome, analysisViewModelFactory)
        }
    }
}
