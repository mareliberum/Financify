package com.example.yandexsummerschool.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.example.yandexsummerschool.R
import com.example.yandexsummerschool.ui.screens.myHistoryScreen.TransactionType
import com.example.yandexsummerschool.ui.theme.iconColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(topAppBarElement: TopAppBarElement, navController: NavController) {
	CenterAlignedTopAppBar(
		colors = TopAppBarDefaults.topAppBarColors(
			containerColor = MaterialTheme.colorScheme.primary,
			titleContentColor = MaterialTheme.colorScheme.onPrimary,
		),
		title = {
			Text(
				text = stringResource(topAppBarElement.titleResource),
				style = MaterialTheme.typography.titleLarge,
			)
		},
		actions = {
			if (topAppBarElement.iconResource != null) {
				IconButton(
					onClick = {
						topAppBarElement.destination?.let {
							navController.navigate(it) {
								popUpTo(navController.graph.findStartDestination().id) {
									saveState = true
								}
								launchSingleTop = true
								restoreState = false
							}
						}
					}
				)
				{
					Icon(
						modifier = Modifier.padding(end = 16.dp),
						painter = painterResource(topAppBarElement.iconResource),
						contentDescription = null,
						tint = iconColor
					)
				}
			}
		}
	)
}

sealed class TopAppBarElement(
	val titleResource: Int,
	val iconResource: Int?,
	val destination: String? = null
) {
	data object Expenses :
		TopAppBarElement(
			titleResource = R.string.Expenses_today,
			iconResource = R.drawable.history,
			destination = "history/${TransactionType.EXPENSE.key}"
		)

	data object Incomes :
		TopAppBarElement(
			titleResource = R.string.Incomes_today,
			iconResource = R.drawable.history,
			destination = "history/${TransactionType.INCOME.key}"
		)

	data object Account :
		TopAppBarElement(
			titleResource = R.string.My_account,
			iconResource = R.drawable.edit,
		)

	data object Settings :
		TopAppBarElement(
			titleResource = R.string.Settings,
			iconResource = null,
		)

	data object Articles :
		TopAppBarElement(
			titleResource = R.string.My_articles,
			iconResource = null,
		)

	data object MyHistory :
		TopAppBarElement(
			titleResource = R.string.My_History,
			iconResource = null, // TODO взять из фигмы
		)
}