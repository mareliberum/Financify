package com.example.yandexsummerschool.ui.screens.addTransactionScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.example.yandexsummerschool.R
import com.example.yandexsummerschool.ui.common.components.BottomNavigationBar
import com.example.yandexsummerschool.ui.common.components.ListItem
import com.example.yandexsummerschool.ui.common.components.ListItemSize
import com.example.yandexsummerschool.ui.common.components.TopAppBar

@Composable
fun AddTransactionScreen(
    navController: NavController,
) {
    val viewModel = remember { AddTransactionScreenViewModel() }

    Scaffold(
        topBar = { AddTransactionTopBar(navController) },
        bottomBar = { BottomNavigationBar(navController) },
    ) { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues),
        ) {
            ListItem(
                title = "Счет",
                trailingText = "Сбербанк", // TODO имя счета
                trailingIcon = painterResource(R.drawable.icon_arrow_right),
                listItemSize = ListItemSize.BIG,
            )
            ListItem(
                title = "Статья",
                trailingText = "Ремонт",
                trailingIcon = painterResource(R.drawable.icon_arrow_right),
                listItemSize = ListItemSize.BIG,
            )
            ListItem(
                title = "Сумма",
                trailingText = "666",
                trailingIcon = painterResource(R.drawable.icon_arrow_right),
                listItemSize = ListItemSize.BIG,
            )
            ListItem(
                title = "Дата",
                trailingText = "01.01.1970",
                trailingIcon = painterResource(R.drawable.icon_arrow_right),
                listItemSize = ListItemSize.BIG,
            )
            ListItem(
                title = "Время",
                trailingText = "23:00",
                trailingIcon = painterResource(R.drawable.icon_arrow_right),
                listItemSize = ListItemSize.BIG,
            )
            ListItem(
                title = "Комментарий",
                trailingIcon = painterResource(R.drawable.icon_arrow_right),
                listItemSize = ListItemSize.BIG,
            )
        }
    }
}

@Composable
fun AddTransactionTopBar(navController: NavController) {
    TopAppBar(
        title = "Мои расходы (tmp)",
        leadingIcon = painterResource(R.drawable.x_icon),
        onLeadingClick = { navController.popBackStack() },
        trailingIcon = painterResource(R.drawable.ok_icon),
        onTrailingClick = { },
    )
}
