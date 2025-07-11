package com.example.yandexsummerschool.ui.features.editTransactions.addTransactionScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.yandexsummerschool.R
import com.example.yandexsummerschool.ui.common.components.BottomNavigationBar
import com.example.yandexsummerschool.ui.common.components.LoadingIndicator
import com.example.yandexsummerschool.ui.common.components.TopAppBar
import com.example.yandexsummerschool.ui.common.screens.ErrorScreen
import com.example.yandexsummerschool.ui.features.editTransactions.common.TransactionEditorScreenContent
import kotlinx.collections.immutable.toImmutableList

@Composable
fun AddTransactionScreen(
    viewModelFactory: ViewModelProvider.Factory,
    navController: NavHostController,
    isIncome: Boolean,
) {
    val viewModel: AddTransactionScreenViewModel = viewModel(factory = viewModelFactory)
    val state by viewModel.state.collectAsStateWithLifecycle()
    val accountName by viewModel.accountName.collectAsStateWithLifecycle()
    var showTimePicker by remember { mutableStateOf(false) }
    var showDatePicker by remember { mutableStateOf(false) }
    var isEditingAmount by remember { mutableStateOf(false) }
    var showArticlesSheet by remember { mutableStateOf(false) }
    var isEditingComment by remember { mutableStateOf(false) }
    val articles by viewModel.articles.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.setIncomeType(isIncome)
        viewModel.loadArticles(isIncome)
    }

    Scaffold(
        topBar = {
            AddTransactionTopBar(
                isIncome = isIncome,
                onCancelClick = { navController.popBackStack() },
                onOkClick = {
                    viewModel.createTransaction()
                    navController.popBackStack()
                },
            )
        },
        bottomBar = { BottomNavigationBar(navController) },
    ) { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues),
        ) {
            when (val currentState = state) {
                is AddTransactionScreenState.Error -> ErrorScreen(currentState.message)
                AddTransactionScreenState.Loading -> LoadingIndicator()
                is AddTransactionScreenState.Content -> {
                    TransactionEditorScreenContent(
                        state = currentState,
                        accountName = accountName,
                        isEditingAmount = isEditingAmount,
                        onEditAmount = { isEditingAmount = it },
                        isEditingComment = isEditingComment,
                        onEditComment = { isEditingComment = it },
                        showTimePicker = showTimePicker,
                        onShowTimePicker = { showTimePicker = it },
                        showDatePicker = showDatePicker,
                        onShowDatePicker = { showDatePicker = it },
                        showArticlesSheet = showArticlesSheet,
                        onShowArticlesSheet = { showArticlesSheet = it },
                        onAmountChange = viewModel::changeAmount,
                        onCommentChange = viewModel::changeComment,
                        onDateChange = viewModel::changeDate,
                        onTimeChange = viewModel::changeTime,
                        onCategoryChange = viewModel::changeCategory,
                        articles = articles.toImmutableList(),
                    )
                }
            }
        }
    }
}

@Composable
fun AddTransactionTopBar(isIncome: Boolean, onCancelClick: () -> Unit, onOkClick: () -> Unit) {
    TopAppBar(
        title = if (isIncome) "Мои доходы" else "Мои расходы",
        leadingIcon = painterResource(R.drawable.x_icon),
        onLeadingClick = onCancelClick,
        trailingIcon = painterResource(R.drawable.ok_icon),
        onTrailingClick = onOkClick,
    )
}
