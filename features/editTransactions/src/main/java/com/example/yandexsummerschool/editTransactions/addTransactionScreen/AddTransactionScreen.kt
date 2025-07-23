package com.example.yandexsummerschool.editTransactions.addTransactionScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.yandexsummerschool.common.R
import com.example.yandexsummerschool.editTransactions.common.SaveAndSendLaterDialog
import com.example.yandexsummerschool.editTransactions.common.TransactionEditorScreenContent
import com.example.yandexsummerschool.ui.common.components.BottomNavigationBar
import com.example.yandexsummerschool.ui.common.components.CustomErrorDialog
import com.example.yandexsummerschool.ui.common.components.LoadingIndicator
import com.example.yandexsummerschool.ui.common.components.TopAppBar
import com.example.yandexsummerschool.ui.common.screens.ErrorScreen
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

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
    val coroutineScope = rememberCoroutineScope()
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var showSaveAndSendLaterDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.setIncomeType(isIncome)
        viewModel.loadArticles(isIncome)
        coroutineScope.launch {
            viewModel.errorEvent.collectLatest { message ->
                errorMessage = message
            }
        }
        coroutineScope.launch {
            viewModel.successEvent.collectLatest {
                navController.popBackStack()
            }
        }
    }

    Scaffold(
        topBar = {
            AddTransactionTopBar(
                isIncome = isIncome,
                onCancelClick = { navController.popBackStack() },
                onOkClick = {
                    viewModel.createTransaction()
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
            if (errorMessage != null) {
                CustomErrorDialog(
                    title = stringResource(R.string.Error),
                    message = errorMessage ?: stringResource(R.string.error),
                    onRetry = {
                        viewModel.createTransaction()
                        errorMessage = null
                    },
                    onDismiss = {
                        showSaveAndSendLaterDialog = true
                        errorMessage = null
                    },
                    confirmButtonText = "Повторить",
                    dismissButtonText = "Закрыть",
                )
            }
            if (showSaveAndSendLaterDialog) {
                SaveAndSendLaterDialog(
                    onRetry = {
                        viewModel.addPendingTransaction()
                        showSaveAndSendLaterDialog = false
                        navController.popBackStack()
                    },
                    onDismiss = {
                        showSaveAndSendLaterDialog = false
                    },
                )
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
