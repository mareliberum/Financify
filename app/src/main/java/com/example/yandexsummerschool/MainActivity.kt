package com.example.yandexsummerschool

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.yandexsummerschool.settings.SettingsScreenViewModel
import com.example.yandexsummerschool.settings.di.DaggerSettingsComponent
import com.example.yandexsummerschool.ui.theme.YandexSummerSchoolTheme
import javax.inject.Inject

class MainActivity : ComponentActivity() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)

        val settingsViewModel: SettingsScreenViewModel by lazy {
            val component = DaggerSettingsComponent.factory().create(applicationContext)
            component.getSettingsViewModelFactory().create(SettingsScreenViewModel::class.java)
        }

        installSplashScreen().setKeepOnScreenCondition {
            settingsViewModel.isDarkTheme.value == null
        }

        enableEdgeToEdge()
        setContent {
            val isDarkTheme by settingsViewModel.isDarkTheme.collectAsStateWithLifecycle()
            YandexSummerSchoolTheme(
                darkTheme = isDarkTheme ?: false,
            ) {
                AppNavGraph(viewModelFactory)
            }
        }
    }
}
