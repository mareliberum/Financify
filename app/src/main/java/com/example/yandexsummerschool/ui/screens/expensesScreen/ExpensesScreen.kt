package com.example.yandexsummerschool.ui.screens.expensesScreen

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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.yandexsummerschool.BottomNavigationBar
import com.example.yandexsummerschool.R
import com.example.yandexsummerschool.TopAppBar
import com.example.yandexsummerschool.TopAppBarElement
import com.example.yandexsummerschool.ui.components.FloatingActionButton
import com.example.yandexsummerschool.ui.components.ListItem
import com.example.yandexsummerschool.ui.components.ListItemData
import com.example.yandexsummerschool.ui.components.TrailingIconArrowRight

@Composable
fun ExpensesScreen(navController: NavController, viewModel: ExpensesScreenViewModel = viewModel()) {
	val expensesState by viewModel.expensesState.collectAsState()

	Scaffold(
		topBar = { TopAppBar(TopAppBarElement.Expenses) },
		bottomBar = { BottomNavigationBar(navController = navController) },
		floatingActionButton = { FloatingActionButton() },
	) { innerPadding ->
		Column(
			modifier = Modifier
				.fillMaxSize()
				.padding(innerPadding)
		) {
			when (val state = expensesState) {
				ExpensesState.Empty -> TODO()
				ExpensesState.Loading -> TODO()
				is ExpensesState.Content -> {
					val expensesList = state.expenses
					val expensesSum = state.expensesSum
					val total = ListItemData(
						title = stringResource(R.string.Sum),
						value = expensesSum
					)

					ListItem(
						total,
						Modifier.background(color = MaterialTheme.colorScheme.secondary)
					)

					LazyColumn(modifier = Modifier.padding(vertical = 3.dp)) {
						items(expensesList) { expense ->
							val listItemData = ListItemData(
								lead = expense.emoji,
								title = expense.categoryName,
								subtitle = expense.comment,
								value = expense.amount,
								trail = { TrailingIconArrowRight() },
							)
							ListItem(listItemData, modifier = Modifier.height(70.dp))
						}
					}
				}
			}
		}
	}
}




