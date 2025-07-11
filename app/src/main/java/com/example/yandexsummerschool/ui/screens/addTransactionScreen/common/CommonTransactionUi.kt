package com.example.yandexsummerschool.ui.screens.addTransactionScreen.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import com.example.yandexsummerschool.R
import com.example.yandexsummerschool.domain.models.ArticleModel
import com.example.yandexsummerschool.ui.common.components.CustomTimePickerDialog
import com.example.yandexsummerschool.ui.common.components.DatePicker
import com.example.yandexsummerschool.ui.common.components.ListItem
import com.example.yandexsummerschool.ui.common.components.ListItemSize
import com.example.yandexsummerschool.ui.common.components.textFieldColors
import com.example.yandexsummerschool.ui.screens.addTransactionScreen.creator.AddTransactionScreenState
import kotlinx.collections.immutable.ImmutableList

@Composable
@Suppress("LongParameterList")
fun TransactionEditorScreenContent(
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
            keyboardActions = androidx.compose.foundation.text.KeyboardActions(onDone = { onEdit(false) }),
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
            keyboardActions = androidx.compose.foundation.text.KeyboardActions(onDone = { onEdit(false) }),
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
