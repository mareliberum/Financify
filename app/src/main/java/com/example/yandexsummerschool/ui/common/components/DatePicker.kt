package com.example.yandexsummerschool.ui.common.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePicker(
    selectedDate: Long,
    onDateSelected: (Long?) -> Unit,
    onDismiss: () -> Unit,
) {
    val datePickerState = rememberDatePickerState(initialSelectedDateMillis = selectedDate)

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            DatePickerButtons(
                onDateSelected = onDateSelected,
                onDismiss = onDismiss,
                datePickerState = datePickerState,
            )
        },
        colors =
            DatePickerDefaults.colors(
                containerColor = MaterialTheme.colorScheme.secondary,
                selectedDayContainerColor = MaterialTheme.colorScheme.primary,
            ),
    ) {
        DatePicker(
            state = datePickerState,
            title = null,
            headline = null,
            showModeToggle = false,
            colors =
                DatePickerDefaults.colors(
                    containerColor = MaterialTheme.colorScheme.secondary,
                    selectedDayContainerColor = MaterialTheme.colorScheme.primary,
                    selectedYearContainerColor = MaterialTheme.colorScheme.primary,
                    selectedYearContentColor = MaterialTheme.colorScheme.onPrimary,
                    todayContentColor = MaterialTheme.colorScheme.onPrimary,
                    todayDateBorderColor = MaterialTheme.colorScheme.primary,
                    selectedDayContentColor = MaterialTheme.colorScheme.onPrimary,
                ),
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerButtons(
    onDateSelected: (Long?) -> Unit,
    onDismiss: () -> Unit,
    datePickerState: DatePickerState,
) {
    Row(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(start = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        DatePickerButton(
            buttonType = ButtonType.CLEAR,
            onDismiss = onDismiss,
            onDateSelected = onDateSelected,
        )

        Row {
            DatePickerButton(buttonType = ButtonType.CANCEL, onDismiss = onDismiss)

            DatePickerButton(
                buttonType = ButtonType.OK,
                onDismiss = onDismiss,
                onDateSelected = onDateSelected,
                datePickerState = datePickerState,
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerButton(
    buttonType: ButtonType,
    onDismiss: () -> Unit,
    onDateSelected: ((Long?) -> Unit)? = null,
    datePickerState: DatePickerState? = null,
) {
    val text =
        when (buttonType) {
            ButtonType.CLEAR -> "Очистить"
            ButtonType.CANCEL -> "Отмена"
            ButtonType.OK -> "ОК"
        }

    TextButton(
        onClick = {
            when (buttonType) {
                ButtonType.CLEAR -> {
                    onDateSelected?.invoke(null)
                    onDismiss()
                }

                ButtonType.CANCEL -> onDismiss()

                ButtonType.OK -> {
                    onDateSelected?.invoke(datePickerState?.selectedDateMillis)
                    onDismiss()
                }
            }
        },
        modifier = Modifier.padding(horizontal = 8.dp),
    ) {
        Text(
            text = text,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.primary,
        )
    }
}

enum class ButtonType {
    CLEAR,
    CANCEL,
    OK,
}
