package com.example.yandexsummerschool.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.yandexsummerschool.ui.screens.accountScreen.account.AccountScreenViewModel
import com.example.yandexsummerschool.ui.screens.accountScreen.editor.EditorAccountScreenViewModel
import com.example.yandexsummerschool.ui.screens.addTransactionScreen.AddTransactionScreenViewModel
import com.example.yandexsummerschool.ui.screens.addTransactionScreen.editor.EditorTransactionScreenViewModel
import com.example.yandexsummerschool.ui.screens.articlesScreen.ArticlesScreenViewModel
import com.example.yandexsummerschool.ui.screens.expensesScreen.ExpensesScreenViewModel
import com.example.yandexsummerschool.ui.screens.incomesScreen.IncomesScreenViewModel
import com.example.yandexsummerschool.ui.screens.myHistoryScreen.MyHistoryScreenViewModel
import com.example.yandexsummerschool.ui.screens.settings.SettingsScreenViewModel
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
    @ViewModelKey(ExpensesScreenViewModel::class)
    fun bindsExpensesScreenViewModel(vm: ExpensesScreenViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(IncomesScreenViewModel::class)
    fun bindsIncomesScreenViewModel(vm: IncomesScreenViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ArticlesScreenViewModel::class)
    fun bindsArticlesScreenViewModel(vm: ArticlesScreenViewModel): ViewModel

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
