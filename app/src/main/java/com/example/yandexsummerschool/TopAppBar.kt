package com.example.yandexsummerschool

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.yandexsummerschool.ui.theme.iconColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(topAppBarElement: TopAppBarElement) {
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
				Icon(
					modifier = Modifier.padding(end = 16.dp),
					painter = painterResource(topAppBarElement.iconResource),
					contentDescription = "TopAppBarIcon",
					tint = iconColor
				)
			}
		}
	)
}

sealed class TopAppBarElement(val titleResource: Int, val iconResource: Int?) {
	data object Expenses :
		TopAppBarElement(
			titleResource = R.string.Expenses_today,
			iconResource = R.drawable.history
		)

	data object Incomes :
		TopAppBarElement(
			titleResource = R.string.Incomes_today,
			iconResource = R.drawable.history,
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
}