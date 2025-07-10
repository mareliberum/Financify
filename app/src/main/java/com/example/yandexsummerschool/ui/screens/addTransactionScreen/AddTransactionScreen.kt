package com.example.yandexsummerschool.ui.screens.addTransactionScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.yandexsummerschool.R
import com.example.yandexsummerschool.ui.common.components.BottomNavigationBar
import com.example.yandexsummerschool.ui.common.components.CustomTimePickerDialog
import com.example.yandexsummerschool.ui.common.components.DatePicker
import com.example.yandexsummerschool.ui.common.components.ListItem
import com.example.yandexsummerschool.ui.common.components.ListItemSize
import com.example.yandexsummerschool.ui.common.components.LoadingIndicator
import com.example.yandexsummerschool.ui.common.components.TopAppBar
import com.example.yandexsummerschool.ui.common.components.textFieldColors
import com.example.yandexsummerschool.ui.common.screens.ErrorScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTransactionScreen(
    viewModelFactory: ViewModelProvider.Factory,
    navController: NavHostController,
    isIncome: Boolean
) {
    val viewModel: AddTransactionScreenViewModel = viewModel(factory = viewModelFactory)
    val state by viewModel.state.collectAsStateWithLifecycle()
    val accountName by viewModel.accountName.collectAsStateWithLifecycle()
    var showTimePicker by remember { mutableStateOf(false) }
    var showDatePicker by remember { mutableStateOf(false) }
    var isEditingAmount by remember { mutableStateOf(false) }
    var showArticlesSheet by remember { mutableStateOf(false) }
    var isEditingComment by remember { mutableStateOf(false) }

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
                }
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
                    ListItem(
                        title = "Счет",
                        trailingText = accountName,
                        trailingIcon = painterResource(R.drawable.icon_arrow_right),
                        listItemSize = ListItemSize.BIG,
                    )
                    ListItem(
                        title = "Статья",
                        trailingText = currentState.transaction.categoryName,
                        trailingIcon = painterResource(R.drawable.icon_arrow_right),
                        listItemSize = ListItemSize.BIG,
                        onClick = { showArticlesSheet = true },
                    )
                    if (isEditingAmount) {
                        TextField(
                            value = currentState.transaction.amount,
                            onValueChange = { viewModel.changeAmount(it) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(ListItemSize.BIG.size),
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(
                                imeAction = ImeAction.Done,
                                keyboardType = KeyboardType.Number
                            ),
                            keyboardActions = KeyboardActions(
                                onDone = { isEditingAmount = false }
                            ),
                            colors = textFieldColors()
                        )
                    } else {
                        ListItem(
                            title = "Сумма",
                            trailingText = currentState.transaction.amount,
                            trailingIcon = painterResource(R.drawable.icon_arrow_right),
                            listItemSize = ListItemSize.BIG,
                            onClick = { isEditingAmount = true },
                        )
                    }
                    ListItem(
                        title = "Дата",
                        trailingText = currentState.transaction.date,
                        trailingIcon = painterResource(R.drawable.icon_arrow_right),
                        listItemSize = ListItemSize.BIG,
                        onClick = {
                            showDatePicker = true
                        }
                    )
                    ListItem(
                        title = "Время",
                        trailingText = currentState.transaction.time,
                        trailingIcon = painterResource(R.drawable.icon_arrow_right),
                        listItemSize = ListItemSize.BIG,
                        onClick = { showTimePicker = true }
                    )
                    if (isEditingComment) {
                        TextField(
                            value = currentState.transaction.comment.orEmpty(),
                            onValueChange = { viewModel.changeComment(it) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(ListItemSize.BIG.size),
                            singleLine = false,
                            keyboardOptions = KeyboardOptions(
                                imeAction = ImeAction.Done,
                                keyboardType = KeyboardType.Text
                            ),
                            keyboardActions = KeyboardActions(
                                onDone = { isEditingComment = false }
                            ),
                            colors = textFieldColors()
                        )
                    } else {
                        ListItem(
                            title = "Комментарий",
                            trailingText = currentState.transaction.comment.orEmpty(),
                            trailingIcon = painterResource(R.drawable.icon_arrow_right),
                            listItemSize = ListItemSize.BIG,
                            onClick = { isEditingComment = true },
                        )
                    }
                    if (showTimePicker) {
                        CustomTimePickerDialog(
                            onConfirm = { hour, minute ->
                                viewModel.changeTime(hour, minute)
                                showTimePicker = false
                            },
                            onDismiss = { showTimePicker = false },
                        )
                    }
                    if (showDatePicker) {
                        DatePicker(
                            selectedDate = System.currentTimeMillis(),
                            onDateSelected = {
                                viewModel.changeDate(it ?: System.currentTimeMillis())
                                showDatePicker = false
                            },
                            onDismiss = {
                                showDatePicker = false
                            },
                        )
                    }
                    if (showArticlesSheet) {
                        ModalBottomSheet(
                            onDismissRequest = { showArticlesSheet = false }
                        ) {
                            val articles by viewModel.articles.collectAsStateWithLifecycle()
                            Column {
                                articles.forEach { article ->
                                    ListItem(
                                        title = article.emoji + " " + article.name,
                                        listItemSize = ListItemSize.BIG,
                                        onClick = {
                                            viewModel.changeCategory(article)
                                            showArticlesSheet = false
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AddTransactionTopBar(isIncome: Boolean, onCancelClick: () -> Unit, onOkClick: () -> Unit) {
    TopAppBar(
        title = if (isIncome) "Мои расходы" else "Мои доходы",
        leadingIcon = painterResource(R.drawable.x_icon),
        onLeadingClick = onCancelClick,
        trailingIcon = painterResource(R.drawable.ok_icon),
        onTrailingClick = onOkClick,
    )
}
