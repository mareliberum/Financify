package com.example.yandexsummerschool.ui.screens.expensesScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.yandexsummerschool.R
import com.example.yandexsummerschool.domain.utils.Currencies
import com.example.yandexsummerschool.ui.common.components.BottomNavigationBar
import com.example.yandexsummerschool.ui.common.components.FloatingActionButton
import com.example.yandexsummerschool.ui.common.components.ListItem
import com.example.yandexsummerschool.ui.common.components.ListItemData
import com.example.yandexsummerschool.ui.common.components.ListItemSize
import com.example.yandexsummerschool.ui.common.components.LoadingIndicator
import com.example.yandexsummerschool.ui.common.components.TopAppBar
import com.example.yandexsummerschool.ui.common.components.TopAppBarElement
import com.example.yandexsummerschool.ui.common.components.TrailingIconArrowRight
import com.example.yandexsummerschool.ui.common.screens.EmptyTransactionsScreen
import com.example.yandexsummerschool.ui.common.screens.ErrorScreen
import com.example.yandexsummerschool.ui.navigation.Routes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpensesScreen(
    navController: NavController,
    viewModel: ExpensesScreenViewModel = hiltViewModel(),
) {
    val expensesState by viewModel.expensesScreenState.collectAsStateWithLifecycle()
    val isRefreshing by viewModel.isRefreshing.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        // TODO здесь и в других местах сделать не рефреш,
        //  а проверку на то, надо ли, если изменилась валюта
        viewModel.loadExpenses()
    }

    Scaffold(
        topBar = { TopAppBar(TopAppBarElement.Expenses, navController) },
        bottomBar = { BottomNavigationBar(navController = navController) },
        floatingActionButton = { FloatingActionButton(onClick = { navController.navigate(Routes.AddTransactionScreen.route) }) },
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding),
        ) {
            // TODO добавить pull to refresh в другие места
            PullToRefreshBox(
                isRefreshing = isRefreshing,
                onRefresh = {
                    viewModel.loadExpenses()
                },
                modifier = Modifier.fillMaxSize(),
            ) {
                when (val state = expensesState) {
                    is ExpensesScreenState.Content -> {
                        val expensesList = state.expenses
                        val expensesSum = state.expensesSum
                        val total =
                            ListItemData(
                                title = stringResource(R.string.Sum),
                                trailingText =
                                "$expensesSum " +
                                    Currencies.resolve(
                                        expensesList.firstOrNull()?.currency ?: Currencies.RUB.code,
                                    ),
                            )
                        LazyColumn(
                            modifier = Modifier.padding(vertical = 3.dp),
                        ) {
                            item {
                                ListItem(
                                    total,
                                    Modifier.background(color = MaterialTheme.colorScheme.secondary),
                                )
                            }
                            items(expensesList) { expense ->
                                val listItemData =
                                    ListItemData(
                                        lead = expense.emoji,
                                        title = expense.categoryName,
                                        subtitle = expense.comment,
                                        trailingText = expense.amount + " " + Currencies.resolve(expense.currency),
                                        trailingIcon = { TrailingIconArrowRight() },
                                    )
                                ListItem(
                                    listItemData,
                                    modifier = Modifier.height(ListItemSize.BIG.size),
                                )
                            }
                        }
                    }

                    is ExpensesScreenState.Error -> ErrorScreen(state.message)
                    ExpensesScreenState.Empty -> {
                        EmptyTransactionsScreen()
                    }

                    ExpensesScreenState.Loading -> LoadingIndicator()
                }
            }
        }
    }
}
