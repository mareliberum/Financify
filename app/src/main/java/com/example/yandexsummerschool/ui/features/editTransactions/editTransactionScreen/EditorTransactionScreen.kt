package com.example.yandexsummerschool.ui.features.editTransactions.editTransactionScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.yandexsummerschool.R
import com.example.yandexsummerschool.domain.utils.date.convertIsoToUiDate
import com.example.yandexsummerschool.domain.utils.date.getTimeFromIsoDate
import com.example.yandexsummerschool.ui.common.components.BottomNavigationBar
import com.example.yandexsummerschool.ui.common.components.CustomErrorDialog
import com.example.yandexsummerschool.ui.common.components.LoadingIndicator
import com.example.yandexsummerschool.ui.common.components.TopAppBar
import com.example.yandexsummerschool.ui.common.screens.ErrorScreen
import com.example.yandexsummerschool.ui.features.editTransactions.addTransactionScreen.AddTransactionScreenState
import com.example.yandexsummerschool.ui.features.editTransactions.common.SaveAndSendLaterDialog
import com.example.yandexsummerschool.ui.features.editTransactions.common.TransactionEditorScreenContent
import com.example.yandexsummerschool.ui.theme.dangerAction
import com.example.yandexsummerschool.ui.theme.white
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun EditorTransactionScreen(
    viewModelFactory: ViewModelProvider.Factory,
    navController: NavHostController,
    transactionId: Int,
    isIncome: Boolean,
) {
    val viewModel: EditorTransactionScreenViewModel = viewModel(factory = viewModelFactory)
    LaunchedEffect(transactionId) {
        viewModel.initTransaction(transactionId)
    }
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
    var showInfoDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
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
            EditorTransactionScreenTopBar(
                title = if (isIncome) stringResource(R.string.My_incomes) else stringResource(R.string.My_expenses),
                onCancelClick = { navController.popBackStack() },
                onOkClick = {
                    viewModel.updateTransaction()
                },
            )
        },
        bottomBar = { BottomNavigationBar(navController) },
    ) { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues),
        ) {
            when (val currentState = state) {
                is EditorTransactionScreenState.Error -> ErrorScreen(currentState.message)
                EditorTransactionScreenState.Loading -> LoadingIndicator()
                is EditorTransactionScreenState.Content -> {
                    TransactionEditorScreenContent(
                        state = AddTransactionScreenState.Content(currentState.transaction),
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
                    Button(
                        onClick = {
                            viewModel.deleteTransaction()
                        },
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .padding(top = 16.dp),
                        colors =
                            ButtonDefaults.buttonColors().copy(
                                containerColor = dangerAction,
                                contentColor = white,
                                disabledContentColor = white,
                                disabledContainerColor = dangerAction,
                            ),
                    ) {
                        Text(stringResource(R.string.Delete_transaction))
                    }
                    InfoBtn { showInfoDialog = true }
                    if (showInfoDialog) {
                        val date = currentState.transaction.lastSyncDate
                        ShowInfoDialog(
                            onClick = { showInfoDialog = false },
                            text = convertIsoToUiDate(date) + " " + getTimeFromIsoDate(date),
                        )
                    }
                }
            }
            if (errorMessage != null) {
                CustomErrorDialog(
                    title = stringResource(R.string.Error),
                    message = errorMessage ?: stringResource(R.string.error),
                    onRetry = {
                        viewModel.updateTransaction()
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
                        viewModel.addPendingTransactionUpdate()
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
fun EditorTransactionScreenTopBar(title: String, onCancelClick: () -> Unit, onOkClick: () -> Unit) {
    TopAppBar(
        title = title,
        leadingIcon = painterResource(R.drawable.x_icon),
        onLeadingClick = onCancelClick,
        trailingIcon = painterResource(R.drawable.ok_icon),
        onTrailingClick = onOkClick,
    )
}

@Composable
fun InfoBtn(onClick: () -> Unit) {
    Box(
        Modifier
            .fillMaxSize()
            .padding(4.dp),
    ) {
        IconButton(
            modifier = Modifier.align(Alignment.BottomEnd),
            onClick = onClick,
        ) {
            Icon(
                imageVector = Icons.Filled.Info,
                contentDescription = stringResource(R.string.info),
            )
        }
    }
}

@Composable
fun ShowInfoDialog(onClick: () -> Unit, text: String) {
    AlertDialog(
        onDismissRequest = onClick,
        title = {
            Text(
                text = "Последняя  синхронизация с сервером",
                style = MaterialTheme.typography.titleMedium,
            )
        },
        text = {
            Text(
                text = text,
                style = MaterialTheme.typography.bodyLarge,
            )
        },
        confirmButton = {
            Button(onClick = onClick) {
                Text("ОК")
            }
        },
    )
}
