package com.example.yandexsummerschool

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ViewModelProvider
import com.example.yandexsummerschool.ui.theme.YandexSummerSchoolTheme
import javax.inject.Inject

class MainActivity : ComponentActivity() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)

        installSplashScreen()
        enableEdgeToEdge()
        setContent {
            YandexSummerSchoolTheme {
                AppNavGraph(viewModelFactory)
            }
        }
    }
}
