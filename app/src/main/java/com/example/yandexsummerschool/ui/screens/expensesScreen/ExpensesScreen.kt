package com.example.yandexsummerschool.ui.screens.expensesScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.yandexsummerschool.R
import com.example.yandexsummerschool.ui.components.BottomNavigationBar
import com.example.yandexsummerschool.ui.components.ErrorScreen
import com.example.yandexsummerschool.ui.components.FloatingActionButton
import com.example.yandexsummerschool.ui.components.ListItem
import com.example.yandexsummerschool.ui.components.ListItemData
import com.example.yandexsummerschool.ui.components.LoadingIndicator
import com.example.yandexsummerschool.ui.components.TopAppBar
import com.example.yandexsummerschool.ui.components.TopAppBarElement
import com.example.yandexsummerschool.ui.components.TrailingIconArrowRight

@Composable
fun ExpensesScreen(
    navController: NavController,
    viewModel: ExpensesScreenViewModel = hiltViewModel(),
) {
    val expensesState by viewModel.expensesScreenState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = { TopAppBar(TopAppBarElement.Expenses, navController) },
        bottomBar = { BottomNavigationBar(navController = navController) },
        floatingActionButton = { FloatingActionButton(navController) },
    ) { innerPadding ->
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
        ) {
            when (val state = expensesState) {
                is ExpensesScreenState.Content -> {
                    val expensesList = state.expenses
                    val expensesSum = state.expensesSum
                    val total =
                        ListItemData(
                            title = stringResource(R.string.Sum),
                            trailingText = expensesSum,
                        )

                    ListItem(
                        total,
                        Modifier.background(color = MaterialTheme.colorScheme.secondary),
                    )

                    LazyColumn(modifier = Modifier.padding(vertical = 3.dp)) {
                        items(expensesList) { expense ->
                            val listItemData =
                                ListItemData(
                                    lead = expense.emoji,
                                    title = expense.categoryName,
                                    subtitle = expense.comment,
                                    trailingText = expense.amount,
                                    trailingIcon = { TrailingIconArrowRight() },
                                )
                            ListItem(
                                listItemData,
                                modifier = Modifier.height(70.dp),
                            )
                        }
                    }
                }

                is ExpensesScreenState.Error -> ErrorScreen("Ошибка")

                ExpensesScreenState.Empty -> {
                    Box(modifier = Modifier.fillMaxSize().align(Alignment.CenterHorizontally)) {
                        Text("Пусто")
                    }
                }
                ExpensesScreenState.Loading -> LoadingIndicator()
            }
        }
    }
}
