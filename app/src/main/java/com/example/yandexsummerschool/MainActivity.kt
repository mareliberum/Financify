package com.example.yandexsummerschool

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.yandexsummerschool.domain.NetworkObserver
import com.example.yandexsummerschool.ui.components.NetworkStatusToast
import com.example.yandexsummerschool.ui.screens.accountScreen.AccountScreen
import com.example.yandexsummerschool.ui.screens.articlesScreen.ArticlesScreen
import com.example.yandexsummerschool.ui.screens.expensesScreen.ExpensesScreen
import com.example.yandexsummerschool.ui.screens.incomesScreen.IncomesScreen
import com.example.yandexsummerschool.ui.screens.myHistoryScreen.MyHistoryScreen
import com.example.yandexsummerschool.ui.screens.myHistoryScreen.TransactionType
import com.example.yandexsummerschool.ui.screens.settings.SettingsScreen
import com.example.yandexsummerschool.ui.theme.YandexSummerSchoolTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		installSplashScreen()
		enableEdgeToEdge()
		setContent {
			YandexSummerSchoolTheme {
				App()
			}
		}
	}
}

@Composable
fun App() {
	val context = LocalContext.current
	val networkObserver = remember { NetworkObserver(context) }
	DisposableEffect(Unit) {
		networkObserver.startObserving()
		onDispose {
			networkObserver.stopObserving()
		}
	}

	NetworkStatusToast(networkObserver)

	val navController = rememberNavController()
	NavHost(navController = navController, startDestination = Routes.ExpensesScreen.route) {
		composable(Routes.ExpensesScreen.route) { ExpensesScreen(navController) }
		composable(Routes.IncomesScreen.route) { IncomesScreen(navController) }
		composable(Routes.SettingsScreen.route) { SettingsScreen(navController) }
		composable(Routes.AccountScreen.route) { AccountScreen(navController) }
		composable(Routes.ExpenseArticleScreen.route) { ArticlesScreen(navController) }
		composable(
			"history/{operationType}",   // TODO поменять на Routes.MyHistoryScreen.Route
			arguments = listOf(navArgument("operationType") { type = NavType.StringType })
		) { backStackEntry ->
			val operationType = backStackEntry.arguments?.getString("operationType")
			val type = TransactionType.entries.find { it.key == operationType } ?: TransactionType.EXPENSE
			MyHistoryScreen(navController, type)
		}
	}
}

sealed class Routes(val route: String) {
	data object ExpensesScreen : Routes("Expenses")
	data object IncomesScreen : Routes("Incomes")
	data object SettingsScreen : Routes("Settings")
	data object AccountScreen : Routes("Account")
	data object ExpenseArticleScreen : Routes("ExpenseArticles")
	data object MyHistoryScreen : Routes("history/{operationType}")
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview() {
	YandexSummerSchoolTheme {
		App()
	}
}