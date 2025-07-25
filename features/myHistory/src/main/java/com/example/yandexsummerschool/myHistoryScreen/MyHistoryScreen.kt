package com.example.yandexsummerschool.myHistoryScreen

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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.yandexsummerschool.common.R
import com.example.yandexsummerschool.domain.utils.Currencies
import com.example.yandexsummerschool.ui.common.DateType
import com.example.yandexsummerschool.ui.common.TransactionType
import com.example.yandexsummerschool.ui.common.components.BottomNavigationBar
import com.example.yandexsummerschool.ui.common.components.DatePicker
import com.example.yandexsummerschool.ui.common.components.ListItem
import com.example.yandexsummerschool.ui.common.components.ListItemData
import com.example.yandexsummerschool.ui.common.components.LoadingIndicator
import com.example.yandexsummerschool.ui.common.components.TopAppBar
import com.example.yandexsummerschool.ui.common.components.TrailingIconArrowRight
import com.example.yandexsummerschool.ui.common.screens.EmptyTransactionsScreen
import com.example.yandexsummerschool.ui.common.screens.ErrorScreen

@Composable
fun MyHistoryScreen(
    navController: NavController,
    transactionType: TransactionType,
    viewModelFactory: ViewModelProvider.Factory,
) {
    val viewModel: MyHistoryScreenViewModel = viewModel(factory = viewModelFactory)
    var showPicker by remember { mutableStateOf(false) }
    var pickerType by remember { mutableStateOf<DateType?>(null) }
    val startOfPeriod by viewModel.startOfPeriod.collectAsStateWithLifecycle()
    val endOfPeriod by viewModel.endOfPeriod.collectAsStateWithLifecycle()
    val currentState = viewModel.myHistoryScreenState.collectAsStateWithLifecycle()
    val startItem = ListItemData(
        title = stringResource(com.example.yandexsummerschool.myHistory.R.string.Start),
        trailingText = startOfPeriod
    )
    val endItem = ListItemData(
        title = stringResource(com.example.yandexsummerschool.myHistory.R.string.End),
        trailingText = endOfPeriod
    )
    val isIncome = transactionType == TransactionType.INCOME

    LaunchedEffect(Unit) {
        viewModel.loadHistory(transactionType)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = stringResource(R.string.My_History),
                leadingIcon = painterResource(R.drawable.arrow_back),
                onLeadingClick = { navController.popBackStack() },
                trailingIcon = painterResource(R.drawable.analysis),
                onTrailingClick = { navController.navigate("AnalysisScreen?isIncome=$isIncome") },
            )
        },
        bottomBar = { BottomNavigationBar(navController) },
    ) { padding ->
        Column(
	        Modifier
		        .fillMaxSize()
		        .padding(padding),
        ) {
            if (showPicker) {
                DatePicker(
                    selectedDate = System.currentTimeMillis(),
                    onDateSelected = {
                        when (pickerType) {
                            DateType.START -> viewModel.setStartDate(it)
                            DateType.END -> viewModel.setEndDate(it)
                            null -> {}
                        }
                        showPicker = false
                    },
                    onDismiss = {
                        showPicker = false
                    },
                )
            }
            // Даты
            ListItem(
                startItem,
                modifier = Modifier.background(MaterialTheme.colorScheme.secondary),
                onClick = {
                    pickerType = DateType.START
                    showPicker = true
                },
            )
            ListItem(
                endItem,
                modifier =
                Modifier
                    .background(MaterialTheme.colorScheme.secondary),
                onClick = {
                    pickerType = DateType.END
                    showPicker = true
                },
            )
            // контент
            when (val state = currentState.value) {
                HistoryScreenState.Empty ->
                    EmptyTransactionsScreen(
                        title = stringResource(R.string.no_transactions_for_period),
                        subtitle = stringResource(R.string.Empty_transaction_screen_subText),
                    )

                HistoryScreenState.Loading -> LoadingIndicator()
                is HistoryScreenState.Content -> {
                    val sumItem =
                        ListItemData(
                            title = stringResource(R.string.Sum),
                            trailingText =
                            state.sum +
                                Currencies.resolve(
                                    state.history.firstOrNull()?.currency ?: Currencies.RUB.code,
                                ),
                        )

                    ListItem(
                        sumItem,
                        modifier = Modifier.background(MaterialTheme.colorScheme.secondary),
                    )

                    LazyColumn {
                        items(state.history) { item ->
                            val listItemData =
                                ListItemData(
                                    lead = item.lead,
                                    title = item.title,
                                    subtitle = item.subtitle,
                                    trailingText = item.sum + Currencies.resolve(item.currency),
                                    trailingSubText = item.date,
                                    trailingIcon = { TrailingIconArrowRight() },
                                )
                            ListItem(
                                listItemData = listItemData,
                                modifier = Modifier.height(70.dp),
                                onClick = {
                                    val isIncome =
                                        transactionType == TransactionType.INCOME
                                    navController.navigate("EditorTransactionScreen/${item.id}?isIncome=$isIncome")
                                },
                            )
                        }
                    }
                }

                is HistoryScreenState.Error -> {
                    ErrorScreen(state.message)
                }
            }
        }
    }
}
