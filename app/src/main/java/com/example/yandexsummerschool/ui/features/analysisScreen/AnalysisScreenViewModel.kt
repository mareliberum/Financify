package com.example.yandexsummerschool.ui.features.analysisScreen

import androidx.lifecycle.viewModelScope
import com.example.yandexsummerschool.data.local.sharedPrefs.UserDelegate
import com.example.yandexsummerschool.domain.models.Result
import com.example.yandexsummerschool.domain.models.TransactionDomainModel
import com.example.yandexsummerschool.domain.useCases.account.GetAccountUseCase
import com.example.yandexsummerschool.domain.useCases.expenses.GetExpensesUseCase
import com.example.yandexsummerschool.domain.useCases.incomes.GetIncomesUseCase
import com.example.yandexsummerschool.domain.useCases.transactions.GroupTransactionsByCategoriesUseCase
import com.example.yandexsummerschool.domain.useCases.transactions.local.get.GetSumByCategoryUseCase
import com.example.yandexsummerschool.domain.utils.date.getStartOfMonth
import com.example.yandexsummerschool.domain.utils.date.makeFullIsoDate
import com.example.yandexsummerschool.domain.utils.date.millsToIsoDateSimple
import com.example.yandexsummerschool.ui.common.BaseViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class AnalysisScreenViewModel @Inject constructor(
    private val getSumByCategoryUseCase: GetSumByCategoryUseCase,
    private val getExpensesUseCase: GetExpensesUseCase,
    private val getIncomesUseCase: GetIncomesUseCase,
    private val groupTransactionsByCategoriesUseCase: GroupTransactionsByCategoriesUseCase,
    override val userDelegate: UserDelegate,
    override val getAccountUseCase: GetAccountUseCase,
) : BaseViewModel() {
    private var loadingJob: Job? = null
    private var isIncome = false
    private val _startOfPeriod = MutableStateFlow(getStartOfMonth())
    val startOfPeriod = _startOfPeriod.asStateFlow()
    private val _endOfPeriod = MutableStateFlow(System.currentTimeMillis())
    val endOfPeriod = _endOfPeriod.asStateFlow()
    private val _state = MutableStateFlow<AnalysisScreenState>(AnalysisScreenState.Loading)
    val state = _state.asStateFlow()

    fun initialize(isIncome: Boolean) {
        this.isIncome = isIncome
        loadData()
    }

    private fun loadData() {
        loadingJob?.cancel()
        loadingJob =
            viewModelScope.launch {
                loadTransactionsFromDb()
                val transactions = loadTransactionsFromApi()
                if (transactions is Result.Success)
                    {
                        val result = groupTransactionsByCategoriesUseCase(transactions.data).map { it.toAnalysisItem() }
                        setContent(result)
                    }
            }
    }

    private suspend fun loadTransactionsFromDb() {
        val result =
            getSumByCategoryUseCase(
                makeFullIsoDate(startOfPeriod.value),
                makeFullIsoDate(endOfPeriod.value),
                isIncome,
            ).map { it.toAnalysisItem() }
        setContent(result)
    }

    private suspend fun loadTransactionsFromApi(): Result<List<TransactionDomainModel>> {
        return when (isIncome) {
            true -> {
                getIncomesUseCase(
                    getAccountId(),
                    millsToIsoDateSimple(startOfPeriod.value),
                    millsToIsoDateSimple(endOfPeriod.value),
                )
            }

            false -> {
                getExpensesUseCase(
                    getAccountId(),
                    millsToIsoDateSimple(startOfPeriod.value),
                    millsToIsoDateSimple(endOfPeriod.value),
                )
            }
        }
    }

    private fun setContent(list: List<AnalysisItemModel>) {
        val sum = list.sumOf { it.amount }
        if (list.isNotEmpty()) {
            _state.value = AnalysisScreenState.Content(list, sum)
        } else
            {
                _state.value = AnalysisScreenState.Empty
            }
    }

    fun setStartDate(dateMillis: Long?) {
        if (dateMillis != null) {
            _startOfPeriod.value = dateMillis
        }
        loadData()
    }

    fun setEndDate(dateMillis: Long?) {
        if (dateMillis != null) {
            _endOfPeriod.value = dateMillis
        }
        loadData()
    }
}
