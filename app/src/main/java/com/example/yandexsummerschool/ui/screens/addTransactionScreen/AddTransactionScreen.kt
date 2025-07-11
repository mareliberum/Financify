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
import com.example.yandexsummerschool.domain.models.ArticleModel
import com.example.yandexsummerschool.ui.common.components.BottomNavigationBar
import com.example.yandexsummerschool.ui.common.components.CustomTimePickerDialog
import com.example.yandexsummerschool.ui.common.components.DatePicker
import com.example.yandexsummerschool.ui.common.components.ListItem
import com.example.yandexsummerschool.ui.common.components.ListItemSize
import com.example.yandexsummerschool.ui.common.components.LoadingIndicator
import com.example.yandexsummerschool.ui.common.components.TopAppBar
import com.example.yandexsummerschool.ui.common.components.textFieldColors
import com.example.yandexsummerschool.ui.common.screens.ErrorScreen
import kotlinx.collections.immutable.ImmutableList
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
                    AddTransactionScreenContent(
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
@Suppress("LongParameterList")
fun AddTransactionScreenContent(
    state: AddTransactionScreenState.Content,
    accountName: String,
    isEditingAmount: Boolean,
    onEditAmount: (Boolean) -> Unit,
    isEditingComment: Boolean,
    onEditComment: (Boolean) -> Unit,
    showTimePicker: Boolean,
    onShowTimePicker: (Boolean) -> Unit,
    showDatePicker: Boolean,
    onShowDatePicker: (Boolean) -> Unit,
    showArticlesSheet: Boolean,
    onShowArticlesSheet: (Boolean) -> Unit,
    onAmountChange: (String) -> Unit,
    onCommentChange: (String) -> Unit,
    onDateChange: (Long) -> Unit,
    onTimeChange: (Int, Int) -> Unit,
    onCategoryChange: (ArticleModel) -> Unit,
    articles: ImmutableList<ArticleModel>,
) {
    AccountSelector(accountName)
    CategorySelector(state.transaction.categoryName) { onShowArticlesSheet(true) }
    AmountEditor(
        amount = state.transaction.amount,
        isEditing = isEditingAmount,
        onEdit = onEditAmount,
        onValueChange = onAmountChange,
    )
    DateSelector(
        date = state.transaction.date,
        onClick = { onShowDatePicker(true) },
    )
    TimeSelector(
        time = state.transaction.time,
        onClick = { onShowTimePicker(true) },
    )
    CommentEditor(
        comment = state.transaction.comment,
        isEditing = isEditingComment,
        onEdit = onEditComment,
        onValueChange = onCommentChange,
    )
    if (showTimePicker) {
        CustomTimePickerDialog(
            onConfirm = { hour, minute ->
                onTimeChange(hour, minute)
                onShowTimePicker(false)
            },
            onDismiss = { onShowTimePicker(false) },
        )
    }
    if (showDatePicker) {
        DatePicker(
            selectedDate = System.currentTimeMillis(),
            onDateSelected = {
                onDateChange(it ?: System.currentTimeMillis())
                onShowDatePicker(false)
            },
            onDismiss = {
                onShowDatePicker(false)
            },
        )
    }
    if (showArticlesSheet) {
        ArticlesSheet(
            articles = articles,
            onSelect = {
                onCategoryChange(it)
                onShowArticlesSheet(false)
            },
            onDismiss = { onShowArticlesSheet(false) },
        )
    }
}

@Composable
fun AccountSelector(accountName: String) {
    ListItem(
        title = "Счет",
        trailingText = accountName,
        trailingIcon = painterResource(R.drawable.icon_arrow_right),
        listItemSize = ListItemSize.BIG,
    )
}

@Composable
fun CategorySelector(categoryName: String, onClick: () -> Unit) {
    ListItem(
        title = "Статья",
        trailingText = categoryName,
        trailingIcon = painterResource(R.drawable.icon_arrow_right),
        listItemSize = ListItemSize.BIG,
        onClick = onClick,
    )
}

@Composable
fun AmountEditor(amount: String, isEditing: Boolean, onEdit: (Boolean) -> Unit, onValueChange: (String) -> Unit) {
    if (isEditing) {
        TextField(
            value = amount,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth().height(ListItemSize.BIG.size),
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done, keyboardType = KeyboardType.Number),
            keyboardActions = KeyboardActions(onDone = { onEdit(false) }),
            colors = textFieldColors(),
        )
    } else {
        ListItem(
            title = "Сумма",
            trailingText = amount,
            trailingIcon = painterResource(R.drawable.icon_arrow_right),
            listItemSize = ListItemSize.BIG,
            onClick = { onEdit(true) },
        )
    }
}

@Composable
fun DateSelector(date: String, onClick: () -> Unit) {
    ListItem(
        title = "Дата",
        trailingText = date,
        trailingIcon = painterResource(R.drawable.icon_arrow_right),
        listItemSize = ListItemSize.BIG,
        onClick = onClick,
    )
}

@Composable
fun TimeSelector(time: String, onClick: () -> Unit) {
    ListItem(
        title = "Время",
        trailingText = time,
        trailingIcon = painterResource(R.drawable.icon_arrow_right),
        listItemSize = ListItemSize.BIG,
        onClick = onClick,
    )
}

@Composable
fun CommentEditor(comment: String?, isEditing: Boolean, onEdit: (Boolean) -> Unit, onValueChange: (String) -> Unit) {
    if (isEditing) {
        TextField(
            value = comment.orEmpty(),
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth().height(ListItemSize.BIG.size),
            singleLine = false,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done, keyboardType = KeyboardType.Text),
            keyboardActions = KeyboardActions(onDone = { onEdit(false) }),
            colors = textFieldColors(),
        )
    } else {
        ListItem(
            title = "Комментарий",
            trailingText = comment.orEmpty(),
            trailingIcon = painterResource(R.drawable.icon_arrow_right),
            listItemSize = ListItemSize.BIG,
            onClick = { onEdit(true) },
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticlesSheet(
    articles: ImmutableList<ArticleModel>,
    onSelect: (ArticleModel) -> Unit,
    onDismiss: () -> Unit,
) {
    ModalBottomSheet(onDismissRequest = onDismiss) {
        Column {
            articles.forEach { article ->
                ListItem(
                    title = article.emoji + " " + article.name,
                    listItemSize = ListItemSize.BIG,
                    onClick = { onSelect(article) },
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
