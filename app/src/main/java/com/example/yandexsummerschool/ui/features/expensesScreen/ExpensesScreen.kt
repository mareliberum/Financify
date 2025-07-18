package com.example.yandexsummerschool.ui.features.expensesScreen

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
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpensesScreen(
    viewModelFactory: ViewModelProvider.Factory,
    navController: NavController,
) {
    val viewModel: ExpensesScreenViewModel = viewModel(factory = viewModelFactory)
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
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate("AddTransactionScreen?isIncome=false") })
        },
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
                        LazyColumn {
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
                                    listItemData = listItemData,
                                    onClick = {
                                        navController.navigate(
                                            "EditorTransactionScreen/${expense.id}?isIncome=false",
                                        )
                                    },
                                    modifier = Modifier.height(ListItemSize.BIG.size),
                                )
                            }
                        }
                    }

                    is ExpensesScreenState.Error -> ErrorScreen(state.message)
                    ExpensesScreenState.Empty -> {
                        EmptyTransactionsScreen(
                            title = stringResource(R.string.empty_transaction_screen_text),
                            subtitle = stringResource(R.string.Empty_transaction_screen_subText),
                        )
                    }

                    ExpensesScreenState.Loading -> LoadingIndicator()
                }
            }
        }
    }
}
