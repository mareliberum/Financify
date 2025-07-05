package com.example.yandexsummerschool.ui.screens.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.yandexsummerschool.ui.common.components.BottomNavigationBar
import com.example.yandexsummerschool.ui.common.components.ListItem
import com.example.yandexsummerschool.ui.common.components.ListItemData
import com.example.yandexsummerschool.ui.common.components.TopAppBar
import com.example.yandexsummerschool.ui.common.components.TopAppBarElement
import com.example.yandexsummerschool.ui.common.components.TrailingIconArrowRightFilled

@Composable
fun SettingsScreen(navController: NavController) {
    Scaffold(
        topBar = { TopAppBar(TopAppBarElement.Settings, navController) },
        bottomBar = { BottomNavigationBar(navController = navController) },
    ) { innerPadding ->
        var checkedState by remember { mutableStateOf(false) }
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
        ) {
            ListItem(
                ListItemData(
                    title = "Тёмная тема",
                    trailingIcon = {
                        Switch(
                            checked = checkedState,
                            onCheckedChange = { checkedState = !checkedState },
                            colors =
                                SwitchDefaults.colors(
                                    checkedThumbColor = MaterialTheme.colorScheme.surface,
                                    uncheckedThumbColor = MaterialTheme.colorScheme.outline,
                                    uncheckedTrackColor = MaterialTheme.colorScheme.outlineVariant,
                                ),
                        )
                    },
                ),
                modifier = Modifier.height(55.dp),
            )
            val someSettingItem =
                ListItemData(
                    title = "mock setting",
                    trailingIcon = { TrailingIconArrowRightFilled() },
                )

            LazyColumn {
                items(9) {
                    ListItem(
                        listItemData = someSettingItem,
                        modifier = Modifier.height(55.dp),
                    )
                }
            }
        }
    }
}
