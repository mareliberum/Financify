package com.example.yandexsummerschool.ui.features.editTransactions.addTransactionScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.yandexsummerschool.domain.models.ArticleModel
import com.example.yandexsummerschool.domain.models.Result
import com.example.yandexsummerschool.domain.useCases.account.GetAccountUseCase
import com.example.yandexsummerschool.domain.useCases.articles.GetArticlesUseCase
import com.example.yandexsummerschool.domain.useCases.transactions.CreateTransactionUseCase
import com.example.yandexsummerschool.domain.utils.Currencies
import com.example.yandexsummerschool.domain.utils.date.millsToUiDate
import com.example.yandexsummerschool.ui.common.ErrorMessageResolver
import com.example.yandexsummerschool.ui.common.uiModels.TransactionUiModel
import com.example.yandexsummerschool.ui.common.uiModels.toTransactionDomainModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject

class AddTransactionScreenViewModel @Inject constructor(
    private val createTransactionUseCase: CreateTransactionUseCase,
    private val getArticlesUseCase: GetArticlesUseCase,
    private val getAccountUseCase: GetAccountUseCase,
) : ViewModel() {
    private var isIncome: Boolean? = null
    private val _state =
        MutableStateFlow<AddTransactionScreenState>(AddTransactionScreenState.Loading)
    val state = _state.asStateFlow()
    private val _accountName = MutableStateFlow("Loading...")
    val accountName = _accountName.asStateFlow()
    private val _articles = MutableStateFlow<List<ArticleModel>>(emptyList())
    val articles = _articles.asStateFlow()
    private val newTransactionModel =
        TransactionUiModel(
            id = "0",
            categoryId = 0,
            categoryName = "",
            amount = "0",
            // В целом валюта не важна,т.к. ее на этом экране поменять нельзя
            currency = Currencies.RUB.code,
            date = millsToUiDate(System.currentTimeMillis()),
            time =
                String.format(
                    Locale.ENGLISH,
                    "%02d:%02d",
                    Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
                    Calendar.getInstance().get(Calendar.MINUTE),
                ),
            isIncome = isIncome ?: false,
        )
    private val _errorEvent = MutableSharedFlow<String>()
    val errorEvent: SharedFlow<String> = _errorEvent.asSharedFlow()
    private val _successEvent = MutableSharedFlow<Unit>()
    val successEvent = _successEvent.asSharedFlow()

    init {
        loadAccount()
    }

    fun createTransaction() {
        val currentState = state.value
        if (currentState is AddTransactionScreenState.Content) {
            viewModelScope.launch {
                val result = createTransactionUseCase(currentState.transaction.toTransactionDomainModel())
                if (result is Result.Failure) {
                    _errorEvent.emit(ErrorMessageResolver.resolve(result.exception))
                } else if (result is Result.Success) {
                    _successEvent.emit(Unit)
                }
            }
        }
    }

    fun setIncomeType(isIncome: Boolean) {
        val currentState = state.value
        if (currentState is AddTransactionScreenState.Content) {
            val updatedTransaction =
                currentState.transaction.copy(
                    isIncome = isIncome,
                )
            _state.value = currentState.copy(transaction = updatedTransaction)
        }
    }

    fun loadArticles(isIncome: Boolean) {
        viewModelScope.launch {
            when (val result = getArticlesUseCase()) {
                is Result.Success -> {
                    _articles.value = result.data.filter { it.isIncome == isIncome }
                    articles.value.firstOrNull()?.let { changeCategory(it) }
                }

                is Result.Failure -> _articles.value = emptyList()
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
                    _state.value = AddTransactionScreenState.Content(newTransactionModel)
                    _accountName.value = result.data.name
                    changeId(result.data.id)
                }
            }
        }
    }

    fun changeTime(hours: Int, minutes: Int) {
        val currentState = state.value
        if (currentState is AddTransactionScreenState.Content) {
            val updatedTransaction =
                currentState.transaction.copy(
                    time = String.format(Locale.ENGLISH, "%02d:%02d", hours, minutes),
                )
            _state.value = currentState.copy(transaction = updatedTransaction)
        }
    }

    fun changeDate(dateMills: Long) {
        val currentState = state.value
        if (currentState is AddTransactionScreenState.Content) {
            val updatedTransaction =
                currentState.transaction.copy(
                    date = millsToUiDate(dateMills),
                )
            _state.value = currentState.copy(transaction = updatedTransaction)
        }
    }

    fun changeAmount(newAmount: String) {
        val currentState = state.value
        if (currentState is AddTransactionScreenState.Content) {
            val updatedTransaction =
                currentState.transaction.copy(
                    amount = newAmount,
                )
            _state.value = currentState.copy(transaction = updatedTransaction)
        }
    }

    fun changeComment(newComment: String) {
        val currentState = state.value
        if (currentState is AddTransactionScreenState.Content) {
            val updatedTransaction =
                currentState.transaction.copy(
                    comment = newComment,
                )
            _state.value = currentState.copy(transaction = updatedTransaction)
        }
    }

    fun changeCategory(article: ArticleModel) {
        val currentState = state.value
        if (currentState is AddTransactionScreenState.Content) {
            val updatedTransaction =
                currentState.transaction.copy(
                    categoryId = article.id,
                    categoryName = article.name,
                    emoji = article.emoji,
                )
            _state.value = currentState.copy(transaction = updatedTransaction)
        }
    }

    private fun changeId(id: Int) {
        val currentState = state.value
        if (currentState is AddTransactionScreenState.Content) {
            val updatedTransaction =
                currentState.transaction.copy(
                    id = id.toString(),
                )

            _state.value = currentState.copy(transaction = updatedTransaction)
        }
    }
}
