package com.example.yandexsummerschool.ui.features.editTransactions.editTransactionScreen

import androidx.lifecycle.viewModelScope
import com.example.yandexsummerschool.data.local.sharedPrefs.UserDelegate
import com.example.yandexsummerschool.domain.models.ArticleModel
import com.example.yandexsummerschool.domain.models.Result
import com.example.yandexsummerschool.domain.useCases.account.GetAccountUseCase
import com.example.yandexsummerschool.domain.useCases.articles.GetArticlesUseCase
import com.example.yandexsummerschool.domain.useCases.transactions.local.insert.InsertPendingUpdateUseCase
import com.example.yandexsummerschool.domain.useCases.transactions.remote.DeleteTransactionUseCase
import com.example.yandexsummerschool.domain.useCases.transactions.remote.GetTransactionByIdUseCase
import com.example.yandexsummerschool.domain.useCases.transactions.remote.UpdateTransactionUseCase
import com.example.yandexsummerschool.domain.utils.date.millsToUiDate
import com.example.yandexsummerschool.ui.common.BaseViewModel
import com.example.yandexsummerschool.ui.common.ErrorMessageResolver
import com.example.yandexsummerschool.ui.common.uiModels.toTransactionUiModel
import com.example.yandexsummerschool.ui.common.uiModels.toUpdatedTransactionDomainModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject

class EditorTransactionScreenViewModel @Inject constructor(
    private val updateTransactionUseCase: UpdateTransactionUseCase,
    private val getArticlesUseCase: GetArticlesUseCase,
    private val getTransactionByIdUseCase: GetTransactionByIdUseCase,
    private val deleteTransactionUseCase: DeleteTransactionUseCase,
    private val insertPendingUpdateUseCase: InsertPendingUpdateUseCase,
    override val userDelegate: UserDelegate,
    override val getAccountUseCase: GetAccountUseCase,
) : BaseViewModel() {
    private val _state = MutableStateFlow<EditorTransactionScreenState>(EditorTransactionScreenState.Loading)
    val state = _state.asStateFlow()
    private val _articles = MutableStateFlow<List<ArticleModel>>(emptyList())
    val articles = _articles.asStateFlow()
    private val _accountName = MutableStateFlow("Loading...")
    val accountName = _accountName.asStateFlow()
    private val _errorEvent = MutableSharedFlow<String>()
    val errorEvent: SharedFlow<String> = _errorEvent.asSharedFlow()
    private val _successEvent = MutableSharedFlow<Unit>()
    val successEvent = _successEvent.asSharedFlow()

    init {
        loadAccount()
    }

    fun updateTransaction() {
        val currentState = state.value
        if (currentState is EditorTransactionScreenState.Content) {
            viewModelScope.launch {
                val result =
                    updateTransactionUseCase(
                        transaction =
                        currentState.transaction.toUpdatedTransactionDomainModel(
                            currentState.transaction.id.toInt(),
                            getAccountId(),
                        ),
                    )
                if (result is Result.Failure) {
                    _errorEvent.emit(ErrorMessageResolver.resolve(result.exception))
                } else if (result is Result.Success) {
                    _successEvent.emit(Unit)
                }
            }
        }
    }

    fun addPendingTransactionUpdate() {
        viewModelScope.launch {
            val currentState = state.value
            if (currentState is EditorTransactionScreenState.Content) {
                val transaction = currentState.transaction
                val updatedTransaction =
                    transaction.toUpdatedTransactionDomainModel(transaction.id.toInt(), getAccountId())
                insertPendingUpdateUseCase(updatedTransaction)
            }
        }
    }

    fun deleteTransaction() {
        val currentState = state.value
        if (currentState is EditorTransactionScreenState.Content) {
            viewModelScope.launch {
                val result =
                    deleteTransactionUseCase(
                        transactionId = currentState.transaction.id.toInt(),
                    )
                if (result is Result.Failure) {
                    _errorEvent.emit(ErrorMessageResolver.resolve(result.exception))
                } else if (result is Result.Success) {
                    _successEvent.emit(Unit)
                }
            }
        }
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
                    _state.value = EditorTransactionScreenState.Error(ErrorMessageResolver.resolve(result.exception))
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
