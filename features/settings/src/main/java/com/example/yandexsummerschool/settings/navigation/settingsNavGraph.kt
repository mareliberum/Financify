package com.example.yandexsummerschool.settings.navigation

import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.yandexsummerschool.settings.di.SettingsDiProvider
import com.example.yandexsummerschool.settings.ui.AboutAppScreen
import com.example.yandexsummerschool.settings.ui.ColorPickerScreen
import com.example.yandexsummerschool.settings.ui.SettingsScreen
import com.example.yandexsummerschool.ui.navigation.Routes

fun NavGraphBuilder.settingsNavGraph(
    navController: NavController,
) {
    navigation(startDestination = SettingsRoutes.SettingsMainScreen.route, route = Routes.SettingsScreen.route) {
        composable(SettingsRoutes.SettingsMainScreen.route) {
            val context = LocalContext.current
            val viewModelFactory = remember { SettingsDiProvider.provideFactory(context) }
            SettingsScreen(navController, viewModelFactory)
        }

        composable(SettingsRoutes.SettingsColorPickerScreen.route) {
            val context = LocalContext.current
            val viewModelFactory = remember { SettingsDiProvider.provideFactory(context) }
            ColorPickerScreen(viewModelFactory) {
                navController.popBackStack()
            }
        }
        composable(SettingsRoutes.SettingAboutAppScreen.route){
            AboutAppScreen { navController.popBackStack() }
        }
    }
}

sealed class SettingsRoutes(val route: String) {
    data object SettingsMainScreen : SettingsRoutes("settings/main")
    data object SettingsColorPickerScreen : SettingsRoutes("settings/color_picker")
    data object SettingAboutAppScreen : SettingsRoutes("settings/about_app")
}
