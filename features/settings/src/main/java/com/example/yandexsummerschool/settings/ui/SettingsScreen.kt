package com.example.yandexsummerschool.settings.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.yandexsummerschool.common.R
import com.example.yandexsummerschool.settings.navigation.SettingsRoutes
import com.example.yandexsummerschool.ui.common.components.BottomNavigationBar
import com.example.yandexsummerschool.ui.common.components.ListItem
import com.example.yandexsummerschool.ui.common.components.ListItemData
import com.example.yandexsummerschool.ui.common.components.ListItemSize
import com.example.yandexsummerschool.ui.common.components.TopAppBar
import com.example.yandexsummerschool.ui.common.components.TopAppBarElement

@Composable
fun SettingsScreen(
    navController: NavController,
    viewModelFactory: ViewModelProvider.Factory,
) {
    val viewModel: SettingsScreenViewModel = viewModel(factory = viewModelFactory)
    val isDarkTheme by viewModel.isDarkTheme.collectAsStateWithLifecycle()
    Scaffold(
        topBar = { TopAppBar(TopAppBarElement.Settings, navController) },
        bottomBar = { BottomNavigationBar(navController = navController) },
    ) { innerPadding ->
        Column(
            modifier =
            Modifier
                .fillMaxSize()
                .padding(innerPadding),
        ) {
            ListItem(
                ListItemData(
                    title = stringResource(com.example.yandexsummerschool.settings.R.string.dark_theme),
                    trailingIcon = {
                        Switch(
                            checked = isDarkTheme == true,
                            onCheckedChange = {
                                viewModel.setDarkTheme(!(isDarkTheme ?: false))
                            },
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
            ListItem(
                title = stringResource(com.example.yandexsummerschool.settings.R.string.Accent_color),
                trailingIcon = painterResource(R.drawable.icon_arrow_right_filled),
                listItemSize = ListItemSize.SMALL,
                onClick = { navController.navigate(SettingsRoutes.SettingsColorPickerScreen.route) }
            )
            ListItem(
                title = stringResource(com.example.yandexsummerschool.settings.R.string.Frequency_Setting),
                trailingIcon = painterResource(R.drawable.icon_arrow_right_filled),
                listItemSize = ListItemSize.SMALL,
                onClick = { navController.navigate(SettingsRoutes.SettingSyncFrequencyScreen.route) }
            )
            ListItem(
                title = stringResource(com.example.yandexsummerschool.settings.R.string.Language),
                trailingIcon = painterResource(R.drawable.icon_arrow_right_filled),
                listItemSize = ListItemSize.SMALL,
                onClick = { navController.navigate(SettingsRoutes.SettingsLanguage.route) }
            )
            ListItem(
                title = stringResource(com.example.yandexsummerschool.settings.R.string.About_App),
                trailingIcon = painterResource(R.drawable.icon_arrow_right_filled),
                listItemSize = ListItemSize.SMALL,
                onClick = { navController.navigate(SettingsRoutes.SettingAboutAppScreen.route) }
            )

        }
    }
}
