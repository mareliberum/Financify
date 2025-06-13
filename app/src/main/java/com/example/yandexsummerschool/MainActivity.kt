package com.example.yandexsummerschool

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.yandexsummerschool.ui.screens.accountScreen.AccountScreen
import com.example.yandexsummerschool.ui.screens.articlesScreen.ArticlesScreen
import com.example.yandexsummerschool.ui.screens.expensesScreen.ExpensesScreen
import com.example.yandexsummerschool.ui.screens.incomesScreen.IncomesScreen
import com.example.yandexsummerschool.ui.screens.settings.SettingsScreen
import com.example.yandexsummerschool.ui.theme.YandexSummerSchoolTheme

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
	val navController = rememberNavController()
	NavHost(navController = navController, startDestination = Routes.ExpensesScreen.route) {
		composable(Routes.ExpensesScreen.route) { ExpensesScreen(navController) }
		composable(Routes.IncomesScreen.route) { IncomesScreen(navController) }
		composable(Routes.SettingsScreen.route) { SettingsScreen(navController) }
		composable(Routes.AccountScreen.route) { AccountScreen(navController) }
		composable(Routes.ExpenseArticleScreen.route) { ArticlesScreen(navController) }
	}
}

sealed class Routes(val route: String) {
	data object ExpensesScreen : Routes("Expenses")
	data object IncomesScreen : Routes("Incomes")
	data object SettingsScreen : Routes("Settings")
	data object AccountScreen : Routes("Account")
	data object ExpenseArticleScreen : Routes("ExpenseArticles")
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview() {
	YandexSummerSchoolTheme {
		App()
	}
}