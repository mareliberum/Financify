package com.example.yandexsummerschool.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.yandexsummerschool.R
import com.example.yandexsummerschool.ui.navigation.Routes
import com.example.yandexsummerschool.ui.screens.myHistoryScreen.TransactionType

@Composable
fun BottomNavigationBar(navController: NavController) {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surfaceContainer,
    ) {
        val backStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = backStackEntry?.destination?.route

        // TODO: немного накостылил, потом подумаю, как можно сделать лучше
        val operationType = backStackEntry?.arguments?.getString("operationType")

        /**
         * Это связано с тем, что экран myHistoryScreen я открываю с пробрасыванием туда ключа -
         * доходы или расходы я отображаю. А иконку в нав баре надо подсвечивать доходов или
         * расходов в соответствии с ключом
         */
        val resolvedRoute =
            when {
                currentRoute == Routes.MyHistoryScreen.route -> {
                    when (operationType) {
                        TransactionType.EXPENSE.key -> Routes.ExpensesScreen.route
                        TransactionType.INCOME.key -> Routes.IncomesScreen.route
                        else -> null
                    }
                }
                else -> currentRoute
            }
        Row(modifier = Modifier.padding(horizontal = 4.dp)) {
            NavBarItems.BarItems.forEach { navItem ->
                val isSelected = resolvedRoute == navItem.route
                NavigationBarItem(
                    selected = isSelected,
                    onClick = {
                        navController.navigate(navItem.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    icon = {
                        Icon(
                            painter = painterResource(navItem.image),
                            contentDescription = navItem.title,
                        )
                    },
                    label = {
                        Text(
                            text = navItem.title,
                            maxLines = 1,
                            color = MaterialTheme.colorScheme.onSurface,
                            style = MaterialTheme.typography.labelMedium,
                            overflow = TextOverflow.Ellipsis,
                        )
                    },
                    colors =
                        NavigationBarItemDefaults.colors(
                            selectedIconColor = MaterialTheme.colorScheme.primary,
                            unselectedIconColor = MaterialTheme.colorScheme.onSurface,
                            indicatorColor = MaterialTheme.colorScheme.secondary,
                        ),
                )
            }
        }
    }
}

object NavBarItems {
    val BarItems =
        listOf(
            BarItem(
                title = "Расходы",
                image = R.drawable.expenses_icon,
                route = Routes.ExpensesScreen.route,
            ),
            BarItem(
                title = "Доходы",
                image = R.drawable.incomes,
                route = Routes.IncomesScreen.route,
            ),
            BarItem(
                title = "Счет",
                image = R.drawable.calculator,
                route = Routes.AccountScreen.route,
            ),
            BarItem(
                title = "Статьи",
                image = R.drawable.articles,
                route = Routes.ExpenseArticleScreen.route,
            ),
            BarItem(
                title = "Настройки",
                image = R.drawable.settings,
                route = Routes.SettingsScreen.route,
            ),
        )
}

data class BarItem(
    val title: String,
    val image: Int,
    val route: String,
)
