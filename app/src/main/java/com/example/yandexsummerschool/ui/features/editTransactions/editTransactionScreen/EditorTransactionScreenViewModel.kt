package com.example.yandexsummerschool.ui.features.editTransactions.editTransactionScreen

import androidx.lifecycle.viewModelScope
import com.example.yandexsummerschool.data.local.UserDelegate
import com.example.yandexsummerschool.domain.models.ArticleModel
import com.example.yandexsummerschool.domain.models.Result
import com.example.yandexsummerschool.domain.useCases.account.GetAccountUseCase
import com.example.yandexsummerschool.domain.useCases.articles.GetArticlesUseCase
import com.example.yandexsummerschool.domain.useCases.transactions.GetTransactionByIdUseCase
import com.example.yandexsummerschool.domain.useCases.transactions.UpdateTransactionUseCase
import com.example.yandexsummerschool.domain.utils.date.millsToUiDate
import com.example.yandexsummerschool.ui.common.BaseViewModel
import com.example.yandexsummerschool.ui.common.uiModels.toCreatedTransactionDomainModel
import com.example.yandexsummerschool.ui.common.uiModels.toTransactionUiModel
import com.example.yandexsummerschool.ui.features.editTransactions.addTransactionScreen.AddTransactionScreenState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject

class EditorTransactionScreenViewModel @Inject constructor(
    private val updateTransactionUseCase: UpdateTransactionUseCase,
    private val getArticlesUseCase: GetArticlesUseCase,
    private val getTransactionByIdUseCase: GetTransactionByIdUseCase,
    override val userDelegate: UserDelegate,
    override val getAccountUseCase: GetAccountUseCase,
) : BaseViewModel() {
    private val _state = MutableStateFlow<EditorTransactionScreenState>(EditorTransactionScreenState.Loading)
    val state = _state.asStateFlow()

    private val _articles = MutableStateFlow<List<ArticleModel>>(emptyList())
    val articles = _articles.asStateFlow()

    private val _accountName = MutableStateFlow("Loading...")
    val accountName = _accountName.asStateFlow()

    init {
        loadAccount()
    }

    fun initTransaction(id: Int) {
        viewModelScope.launch {
            _state.value = EditorTransactionScreenState.Loading
            when (val result = getTransactionByIdUseCase(id)) {
                is Result.Success -> {
                    val transaction = result.data.toTransactionUiModel()
                    _state.value = EditorTransactionScreenState.Content(transaction)
                    loadArticles(transaction.isIncome)
                }

                is Result.Failure -> {
                    _state.value =
                        EditorTransactionScreenState.Error(result.exception.message ?: "Ошибка загрузки транзакции")
                }
            }
        }
    }

    private fun loadAccount() {
        viewModelScope.launch {
            when (val result = getAccountUseCase()) {
                is Result.Failure -> {
                    AddTransactionScreenState.Error("Server is busy")
                }

                is Result.Success -> {
                    _accountName.value = result.data.name
                }
            }
        }
    }

    private fun loadArticles(isIncome: Boolean) {
        viewModelScope.launch {
            when (val result = getArticlesUseCase()) {
                is Result.Success -> {
                    _articles.value = result.data.filter { it.isIncome == isIncome }
                }

                is Result.Failure -> _articles.value = emptyList()
            }
        }
    }

    fun updateTransaction() {
        val currentState = state.value
        if (currentState is EditorTransactionScreenState.Content) {
            viewModelScope.launch {
                updateTransactionUseCase(
                    transactionId = currentState.transaction.id.toInt(),
                    transaction = currentState.transaction.toCreatedTransactionDomainModel(getAccountId()),
                )
            }
        }
    }

    fun changeTime(hours: Int, minutes: Int) {
        val currentState = state.value
        if (currentState is EditorTransactionScreenState.Content) {
            val updatedTransaction =
                currentState.transaction.copy(
                    time = String.format(Locale.ENGLISH, "%02d:%02d", hours, minutes),
                )
            _state.value = currentState.copy(transaction = updatedTransaction)
        }
    }

    fun changeDate(dateMills: Long) {
        val currentState = state.value
        if (currentState is EditorTransactionScreenState.Content) {
            val updatedTransaction =
                currentState.transaction.copy(
                    date = millsToUiDate(dateMills),
                )
            _state.value = currentState.copy(transaction = updatedTransaction)
        }
    }

    fun changeAmount(newAmount: String) {
        val currentState = state.value
        if (currentState is EditorTransactionScreenState.Content) {
            val updatedTransaction =
                currentState.transaction.copy(
                    amount = newAmount,
                )
            _state.value = currentState.copy(transaction = updatedTransaction)
        }
    }

    fun changeComment(newComment: String) {
        val currentState = state.value
        if (currentState is EditorTransactionScreenState.Content) {
            val updatedTransaction =
                currentState.transaction.copy(
                    comment = newComment,
                )
            _state.value = currentState.copy(transaction = updatedTransaction)
        }
    }

    fun changeCategory(article: ArticleModel) {
        val currentState = state.value
        if (currentState is EditorTransactionScreenState.Content) {
            val updatedTransaction =
                currentState.transaction.copy(
                    categoryId = article.id,
                    categoryName = article.name,
                    emoji = article.emoji,
                )
            _state.value = currentState.copy(transaction = updatedTransaction)
        }
    }
}
