package com.example.yandexsummerschool.ui.features.incomesScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
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
import com.example.yandexsummerschool.ui.common.components.LoadingIndicator
import com.example.yandexsummerschool.ui.common.components.TopAppBar
import com.example.yandexsummerschool.ui.common.components.TopAppBarElement
import com.example.yandexsummerschool.ui.common.components.TrailingIconArrowRight
import com.example.yandexsummerschool.ui.common.screens.EmptyTransactionsScreen
import com.example.yandexsummerschool.ui.common.screens.ErrorScreen

@Composable
fun IncomesScreen(
    navController: NavController,
    viewModelFactory: ViewModelProvider.Factory,
) {
    val viewModel: IncomesScreenViewModel = viewModel(factory = viewModelFactory)
    val incomeState by viewModel.incomesState.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) {
        viewModel.loadIncomes()
    }
    Scaffold(
        topBar = { TopAppBar(TopAppBarElement.Incomes, navController) },
        bottomBar = { BottomNavigationBar(navController = navController) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate("AddTransactionScreen?isIncome=true") },
            )
        },
    ) { innerPadding ->
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
        ) {
            when (val state = incomeState) {
                is IncomesScreenState.Content -> {
                    val incomesList = state.incomes
                    val incomesSum = state.incomeSum
                    val total =
                        ListItemData(
                            title = stringResource(R.string.Sum),
                            trailingText =
                                incomesSum + " ${
                                    Currencies.resolve
                                        (state.incomes.firstOrNull()?.currency ?: Currencies.RUB.code)
                                }",
                        )

                    ListItem(
                        total,
                        Modifier.background(color = MaterialTheme.colorScheme.secondary),
                    )

                    LazyColumn(modifier = Modifier.padding(vertical = 3.dp)) {
                        items(incomesList) { income ->
                            val listItemData =
                                ListItemData(
                                    lead = income.emoji,
                                    title = income.categoryName,
                                    subtitle = income.comment,
                                    trailingText = income.amount + " ${Currencies.resolve(income.currency)}",
                                    trailingIcon = { TrailingIconArrowRight() },
                                )
                            ListItem(
                                listItemData = listItemData,
                                modifier = Modifier.height(70.dp),
                                onClick = {
                                    navController.navigate(
                                        "EditorTransactionScreen/${income.id}?isIncome=true",
                                    )
                                },
                            )
                        }
                    }
                }

                IncomesScreenState.Empty -> EmptyTransactionsScreen()
                IncomesScreenState.Loading -> LoadingIndicator()
                is IncomesScreenState.Error -> ErrorScreen("Ошибка")
            }
        }
    }
}
