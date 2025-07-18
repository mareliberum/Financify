package com.example.yandexsummerschool.ui.features.analysisScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.yandexsummerschool.R
import com.example.yandexsummerschool.domain.utils.date.millsToUiDate
import com.example.yandexsummerschool.ui.common.DateType
import com.example.yandexsummerschool.ui.common.components.BottomNavigationBar
import com.example.yandexsummerschool.ui.common.components.DatePicker
import com.example.yandexsummerschool.ui.common.components.ListItem
import com.example.yandexsummerschool.ui.common.components.ListItemData
import com.example.yandexsummerschool.ui.common.components.ListItemSize
import com.example.yandexsummerschool.ui.common.components.LoadingIndicator
import com.example.yandexsummerschool.ui.common.components.TopAppBar
import com.example.yandexsummerschool.ui.common.components.TrailingIconArrowRight
import com.example.yandexsummerschool.ui.common.screens.EmptyTransactionsScreen
import com.example.yandexsummerschool.ui.common.screens.ErrorScreen
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

@Composable
fun AnalysisScreen(
    navController: NavController,
    isIncome: Boolean,
    viewModelFactory: ViewModelProvider.Factory,
) {
    val viewModel: AnalysisScreenViewModel = viewModel(factory = viewModelFactory)
    val screenState = viewModel.state.collectAsStateWithLifecycle()
    val startDate by viewModel.startOfPeriod.collectAsStateWithLifecycle()
    val endDate by viewModel.endOfPeriod.collectAsStateWithLifecycle()
    var showPicker by remember { mutableStateOf(false) }
    var pickerType by remember { mutableStateOf<DateType?>(null) }

    LaunchedEffect(Unit) {
        viewModel.initialize(isIncome)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = "Анализ",
                leadingIcon = painterResource(R.drawable.arrow_back),
                onLeadingClick = { navController.popBackStack() },
            )
        },
        bottomBar = { BottomNavigationBar(navController) },
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            PeriodListItem(
                title = "Период: начало",
                dateText = millsToUiDate(startDate),
                onClick = {
                    pickerType = DateType.START
                    showPicker = true
                },
            )
            PeriodListItem(
                title = "Период: конец",
                dateText = millsToUiDate(endDate),
                onClick = {
                    pickerType = DateType.END
                    showPicker = true
                },
            )

            when (val currentState = screenState.value) {
                is AnalysisScreenState.Error -> ErrorScreen(currentState.message)
                AnalysisScreenState.Loading -> LoadingIndicator()
                is AnalysisScreenState.Content -> {
                    val sum = currentState.sum
                    val items = currentState.items
                    AnalysisScreenContent(sum, items.toImmutableList())
                }

                AnalysisScreenState.Empty ->
                    EmptyTransactionsScreen(
                        title = stringResource(R.string.no_transactions_for_period),
                        subtitle = stringResource(R.string.empty_analysis_subtitle),
                    )
            }
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
        }
    }
}

@Composable
fun AnalysisScreenContent(sum: Double, categoryItems: ImmutableList<AnalysisItemModel>) {
    ListItem(
        title = stringResource(R.string.Sum),
        trailingText = sum.toString(),
        listItemSize = ListItemSize.SMALL,
    )
    LazyColumn {
        items(categoryItems) { item ->
            val itemData =
                ListItemData(
                    lead = item.leadingEmoji,
                    title = item.categoryName,
                    trailingText = item.amount.toString(),
                    trailingSubText = "${(item.amount / sum * 100).toInt()} %", // TODO в viewmodel
                    trailingIcon = { TrailingIconArrowRight() },
                )
            ListItem(itemData, Modifier.height(ListItemSize.BIG.size))
        }
    }
}

@Composable
private fun PeriodListItem(
    title: String,
    dateText: String,
    onClick: () -> Unit,
) {
    ListItem(
        modifier = Modifier.fillMaxWidth(),
        body = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.weight(1f),
                )
                DateItem(dateText)
            }
        },
        onClick = onClick,
    )
}

@Composable
private fun DateItem(text: String) {
    Box(
        modifier =
            Modifier
                .padding(start = 8.dp)
                .wrapContentWidth()
                .background(
                    color = MaterialTheme.colorScheme.primary,
                    shape = CircleShape,
                )
                .padding(horizontal = 16.dp, vertical = 6.dp),
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onPrimary,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}
