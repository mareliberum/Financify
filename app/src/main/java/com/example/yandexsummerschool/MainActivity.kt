package com.example.yandexsummerschool

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.yandexsummerschool.settings.di.DaggerSettingsComponent
import com.example.yandexsummerschool.settings.ui.SettingsScreenViewModel
import com.example.yandexsummerschool.ui.NetworkObserver
import com.example.yandexsummerschool.ui.common.components.NetworkStatusToast
import com.example.yandexsummerschool.ui.theme.GreenPrimary
import com.example.yandexsummerschool.ui.theme.YandexSummerSchoolTheme
import com.example.yandexsummerschool.work_manager.NetworkSyncHandler
import com.example.yandexsummerschool.work_manager.PERIODICAL_SYNC_WORK
import com.example.yandexsummerschool.work_manager.SynchronizeWorkManager
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
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
            settingsViewModel.isDarkTheme.value == null &&
                settingsViewModel.accentColor.value == null
        }


        enableEdgeToEdge()
        setContent {
            val accentColor by settingsViewModel.accentColor.collectAsStateWithLifecycle()
            val isDarkTheme by settingsViewModel.isDarkTheme.collectAsStateWithLifecycle()
            val syncFrequency by settingsViewModel.getSyncFrequency.collectAsStateWithLifecycle()

            YandexSummerSchoolTheme(
                darkTheme = isDarkTheme ?: false,
                accentColor = Color(accentColor?.toULong() ?: GreenPrimary.value),
            ) {
                val context = LocalContext.current
                val networkObserver = remember { NetworkObserver(context) }
                val isConnected by networkObserver.isConnected.collectAsStateWithLifecycle()
                DisposableEffect(Unit) {
                    networkObserver.startObserving()
                    onDispose {
                        networkObserver.stopObserving()
                    }
                }

                LaunchedEffect(syncFrequency) {
                    val workerRequest =
                        PeriodicWorkRequestBuilder<SynchronizeWorkManager>(
                            syncFrequency?.toLong() ?: 60,
                            TimeUnit.MINUTES,
                        )
                            .build()

                    WorkManager.getInstance(context.applicationContext).enqueueUniquePeriodicWork(
                        PERIODICAL_SYNC_WORK,
                        ExistingPeriodicWorkPolicy.UPDATE,
                        workerRequest,
                    )
                }
                NetworkSyncHandler(isConnected)
                NetworkStatusToast(isConnected)

                AppNavGraph(viewModelFactory)
            }
        }
    }
}


