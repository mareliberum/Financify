package com.example.yandexsummerschool.ui.screens.accountScreen.account

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.yandexsummerschool.R
import com.example.yandexsummerschool.domain.utils.Currencies
import com.example.yandexsummerschool.ui.common.components.BottomNavigationBar
import com.example.yandexsummerschool.ui.common.components.FloatingActionButton
import com.example.yandexsummerschool.ui.common.components.ListItem
import com.example.yandexsummerschool.ui.common.components.ListItemData
import com.example.yandexsummerschool.ui.common.components.TopAppBar
import com.example.yandexsummerschool.ui.common.components.TrailingIconArrowRight
import com.example.yandexsummerschool.ui.common.screens.ErrorScreen
import com.example.yandexsummerschool.ui.navigation.Routes
import com.example.yandexsummerschool.ui.screens.accountScreen.components.BottomSheetContent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountScreen(
    navController: NavController,
    viewModel: AccountScreenViewModel = hiltViewModel(),
) {
    val accountState by viewModel.accountState.collectAsStateWithLifecycle()
    var showBottomSheet by remember { mutableStateOf(false) }
    val title by viewModel.accountTitle.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.loadAccountData()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = title,
                trailingIcon = painterResource(R.drawable.edit),
                onTrailingClick = { navController.navigate(Routes.EditorAccountScreen.route) },
            )
        },
        bottomBar = { BottomNavigationBar(navController = navController) },
        floatingActionButton = { FloatingActionButton() },
    ) { innerPadding ->
        Column(
            modifier =
            Modifier
                .fillMaxSize()
                .padding(innerPadding),
        ) {
            when (val state = accountState) {
                AccountScreenState.Empty -> ErrorScreen(stringResource(R.string.No_account_exception))
                is AccountScreenState.Error -> ErrorScreen(state.message)
                AccountScreenState.Loading -> {
                    CircularProgressIndicator()
                }

                is AccountScreenState.Content -> {
                    // TODO можно вынести контент как отдельную composable?
                    val currencySymbol = Currencies.resolve(viewModel.currency.value)
                    val balanceBlock =
                        ListItemData(
                            lead = "💰",
                            title = stringResource(R.string.Balance),
                            trailingText = state.model.balance + " $currencySymbol",
                            trailingIcon = { TrailingIconArrowRight() },
                        )
                    val currencyBlock =
                        ListItemData(
                            title = stringResource(R.string.Currency),
                            trailingText = currencySymbol,
                            trailingIcon = { TrailingIconArrowRight() },
                        )

                    ListItem(
                        listItemData = balanceBlock,
                        modifier = Modifier.background(color = MaterialTheme.colorScheme.secondary),
                        leadingIconBackground = MaterialTheme.colorScheme.surface,
                        onClick = { navController.navigate(Routes.EditorAccountScreen.route) },
                    )
                    ListItem(
                        listItemData = currencyBlock,
                        modifier = Modifier.background(color = MaterialTheme.colorScheme.secondary),
                        onClick = { showBottomSheet = true },
                    )
                    if (showBottomSheet) {
                        ModalBottomSheet(
                            onDismissRequest = { showBottomSheet = false },
                        ) {
                            BottomSheetContent(
                                onCurrencySelected = { viewModel.updateCurrency(it) },
                                onCancel = { showBottomSheet = false },
                            )
                        }
                    }
                }
            }
        }
    }
}
