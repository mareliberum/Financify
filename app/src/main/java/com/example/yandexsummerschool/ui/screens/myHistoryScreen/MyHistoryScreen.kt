package com.example.yandexsummerschool.ui.screens.myHistoryScreen

import androidx.compose.foundation.background
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.yandexsummerschool.ui.components.BottomNavigationBar
import com.example.yandexsummerschool.ui.components.DatePicker
import com.example.yandexsummerschool.ui.components.ListItem
import com.example.yandexsummerschool.ui.components.ListItemData
import com.example.yandexsummerschool.ui.components.LoadingIndicator
import com.example.yandexsummerschool.ui.components.TopAppBar
import com.example.yandexsummerschool.ui.components.TopAppBarElement
import com.example.yandexsummerschool.ui.components.TrailingIconArrowRight

@Composable
fun MyHistoryScreen(
    navController: NavController,
    transactionType: TransactionType,
    viewModel: MyHistoryScreenViewModel = hiltViewModel(),
) {
    var showPicker by remember { mutableStateOf(false) }
    var pickerType by remember { mutableStateOf<DateType?>(null) }
    val startOfPeriod by viewModel.startOfPeriod.collectAsStateWithLifecycle()
    val endOfPeriod by viewModel.endOfPeriod.collectAsStateWithLifecycle()
    val currentState = viewModel.myHistoryScreenState.collectAsStateWithLifecycle()
    val startItem = ListItemData(title = "Начало", trailingText = startOfPeriod)
    val endItem = ListItemData(title = "Конец", trailingText = endOfPeriod)

    Scaffold(
        topBar = { TopAppBar(TopAppBarElement.MyHistory, navController) },
        bottomBar = { BottomNavigationBar(navController) },
    ) { padding ->
        LaunchedEffect(Unit) {
            viewModel.loadHistory(transactionType)
        }

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
                    pickerType = DateType.START
                    showPicker = true
                },
            )
            // контент
            when (val state = currentState.value) {
                HistoryScreenState.Empty -> Text("Пусто")
                HistoryScreenState.Loading -> LoadingIndicator()
                is HistoryScreenState.Content -> {
                    val sumItem = ListItemData(title = "Сумма", trailingText = state.sum)

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
                                    trailingText = item.sum,
                                    trailingSubText = item.time,
                                    trailingIcon = { TrailingIconArrowRight() },
                                )
                            ListItem(
                                listItemData = listItemData,
                                modifier = Modifier.height(70.dp),
                            )
                        }
                    }
                }
            }
        }
    }
}

enum class DateType {
    START,
    END,
}
