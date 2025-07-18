package com.example.yandexsummerschool.ui.features.accountScreen.editor

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.yandexsummerschool.R
import com.example.yandexsummerschool.domain.utils.Currencies
import com.example.yandexsummerschool.ui.common.components.BottomNavigationBar
import com.example.yandexsummerschool.ui.common.components.ListItem
import com.example.yandexsummerschool.ui.common.components.ListItemSize
import com.example.yandexsummerschool.ui.common.components.LoadingIndicator
import com.example.yandexsummerschool.ui.common.components.TopAppBar
import com.example.yandexsummerschool.ui.common.components.textFieldColors
import com.example.yandexsummerschool.ui.common.screens.ErrorScreen
import com.example.yandexsummerschool.ui.features.accountScreen.account.AccountScreenState
import com.example.yandexsummerschool.ui.features.accountScreen.components.BottomSheetContent
import com.example.yandexsummerschool.ui.theme.dangerAction

@Composable
fun EditorAccountScreen(
    navController: NavController,
    viewModelFactory: ViewModelProvider.Factory,
) {
    val viewModel: EditorAccountScreenViewModel = viewModel(factory = viewModelFactory)
    val state by viewModel.accountState.collectAsStateWithLifecycle()
    var showBottomSheet by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            EditorTopBar(navController) { viewModel.confirmUpdates() }
        },
        bottomBar = { BottomNavigationBar(navController = navController) },
    ) { innerPadding ->
        EditorContent(
            state = state,
            viewModel = viewModel,
            innerPadding = innerPadding,
            showBottomSheet = showBottomSheet,
            onBottomSheetDismiss = { showBottomSheet = false },
            onCurrencyClick = { showBottomSheet = true },
        )
    }
}

@Composable
private fun EditorTopBar(navController: NavController, onTrailingClick: () -> Unit) {
    TopAppBar(
        title = "Редактирование",
        leadingIcon = painterResource(R.drawable.x_icon),
        onLeadingClick = { navController.popBackStack() },
        trailingIcon = painterResource(R.drawable.ok_icon),
        onTrailingClick = {
            onTrailingClick()
            navController.popBackStack()
        },
    )
}

@Suppress("LongParameterList")
@Composable
private fun EditorContent(
    state: AccountScreenState,
    viewModel: EditorAccountScreenViewModel,
    innerPadding: PaddingValues,
    showBottomSheet: Boolean,
    onBottomSheetDismiss: () -> Unit,
    onCurrencyClick: () -> Unit,
) {
    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(innerPadding),
    ) {
        when (state) {
            AccountScreenState.Empty -> ErrorScreen(stringResource(R.string.No_account_exception))
            is AccountScreenState.Error -> ErrorScreen(state.message)
            AccountScreenState.Loading -> LoadingIndicator()
            is AccountScreenState.Content -> {
                AccountNameField(viewModel)
                BalanceField(viewModel)
                CurrencyField(viewModel.currency.value, onCurrencyClick)
                DeleteAccountButton()

                if (showBottomSheet) {
                    CurrencyBottomSheet(
                        onDismissRequest = onBottomSheetDismiss,
                        onCurrencySelected = { viewModel.updateCurrency(it) },
                    )
                }
            }
        }
    }
}

@Composable
private fun AccountNameField(viewModel: EditorAccountScreenViewModel) {
    var isEditing by remember { mutableStateOf(false) }
    val accountName by viewModel.accountName

    if (isEditing) {
        TextField(
            value = accountName,
            onValueChange = viewModel::updateAccountName,
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(ListItemSize.SMALL.size),
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = { isEditing = false }),
            colors = textFieldColors(),
        )
    } else {
        ListItem(
            title = stringResource(R.string.name),
            trailingText = accountName,
            listItemSize = ListItemSize.SMALL,
            onClick = { isEditing = true },
        )
    }
}

@Composable
private fun BalanceField(viewModel: EditorAccountScreenViewModel) {
    var isEditing by remember { mutableStateOf(false) }
    val balance by viewModel.balance

    if (isEditing) {
        TextField(
            value = balance,
            onValueChange = { viewModel.updateBalance(it) },
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(ListItemSize.SMALL.size),
            singleLine = true,
            keyboardOptions =
                KeyboardOptions(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Number,
                ),
            keyboardActions = KeyboardActions(onDone = { isEditing = false }),
            colors = textFieldColors(),
        )
    } else {
        ListItem(
            title = stringResource(R.string.Balance),
            leadingIcon = painterResource(R.drawable.person_16dp),
            leadingIconBackground = MaterialTheme.colorScheme.background,
            listItemSize = ListItemSize.SMALL,
            trailingText = balance,
            onClick = { isEditing = true },
        )
    }
}

@Composable
private fun CurrencyField(currency: String, onClick: () -> Unit) {
    ListItem(
        title = stringResource(R.string.currency),
        trailingText = Currencies.resolve(currency),
        listItemSize = ListItemSize.SMALL,
        onClick = onClick,
    )
}

@Composable
private fun DeleteAccountButton() {
    val context = LocalContext.current
    Button(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 16.dp),
        colors =
            ButtonDefaults.buttonColors(
                contentColor = MaterialTheme.colorScheme.surface,
                containerColor = dangerAction,
            ),
        onClick = {
            Toast.makeText(context, context.getString(R.string.dont_delete_account_message), Toast.LENGTH_SHORT).show()
        },
    ) {
        Text(stringResource(R.string.delete_account))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CurrencyBottomSheet(
    onDismissRequest: () -> Unit,
    onCurrencySelected: (String) -> Unit,
) {
    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
    ) {
        BottomSheetContent(onCurrencySelected, onDismissRequest)
    }
}
