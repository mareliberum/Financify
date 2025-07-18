package com.example.yandexsummerschool.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.yandexsummerschool.ui.features.accountScreen.account.AccountScreenViewModel
import com.example.yandexsummerschool.ui.features.accountScreen.editor.EditorAccountScreenViewModel
import com.example.yandexsummerschool.ui.features.editTransactions.addTransactionScreen.AddTransactionScreenViewModel
import com.example.yandexsummerschool.ui.features.editTransactions.editTransactionScreen.EditorTransactionScreenViewModel
import com.example.yandexsummerschool.ui.features.myHistoryScreen.MyHistoryScreenViewModel
import com.example.yandexsummerschool.ui.features.settings.SettingsScreenViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {
    @Binds
    fun bindsViewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(AddTransactionScreenViewModel::class)
    fun bindsAddTransactionScreenViewModel(vm: AddTransactionScreenViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(EditorTransactionScreenViewModel::class)
    fun bindsEditorTransactionScreenViewModel(vm: EditorTransactionScreenViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MyHistoryScreenViewModel::class)
    fun bindsMyHistoryScreenViewModel(vm: MyHistoryScreenViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(EditorAccountScreenViewModel::class)
    fun bindsEditorAccountScreenViewModel(vm: EditorAccountScreenViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AccountScreenViewModel::class)
    fun bindsAccountScreenViewModel(vm: AccountScreenViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SettingsScreenViewModel::class)
    fun bindsSettingsScreenViewModel(vm: SettingsScreenViewModel): ViewModel
}
