package com.example.yandexsummerschool.ui.screens.accountScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
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
fun AccountScreen(navController: NavController, viewModel: AccountScreenViewModel = viewModel()) {
	val accountState by viewModel.accountState.collectAsState()

	Scaffold(
		topBar = { TopAppBar(TopAppBarElement.Account) },
		bottomBar = { BottomNavigationBar(navController = navController) },
		floatingActionButton = { FloatingActionButton() }
	) { innerPadding ->
		Column(
			modifier = Modifier
				.fillMaxSize()
				.padding(innerPadding)
		) {
			when (val state = accountState) {
				AccountState.Empty -> TODO()
				AccountState.Loading -> TODO()
				is AccountState.Content -> {
					val currencySymbol = Currency.resolve(state.currency)
					val balanceBlock = ListItemData(
						lead = "💰",
						title = stringResource(R.string.Balance),
						value = state.balance + " $currencySymbol",
						trail = { TrailingIconArrowRight() },
					)
					val currencyBlock = ListItemData(
						title = stringResource(R.string.Currency),
						value = currencySymbol,
						trail = { TrailingIconArrowRight() },
					)

					ListItem(
						listItemData = balanceBlock,
						modifier = Modifier.background(color = MaterialTheme.colorScheme.secondary),
						leadingIconBackground = MaterialTheme.colorScheme.surface,
					)
					ListItem(
						listItemData = currencyBlock,
						modifier = Modifier.background(color = MaterialTheme.colorScheme.secondary),
					)
				}
			}
		}
	}
}