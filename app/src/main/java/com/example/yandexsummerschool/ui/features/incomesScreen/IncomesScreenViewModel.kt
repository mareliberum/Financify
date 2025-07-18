package com.example.yandexsummerschool.ui.features.incomesScreen

import androidx.lifecycle.viewModelScope
import com.example.yandexsummerschool.data.local.sharedPrefs.UserDelegate
import com.example.yandexsummerschool.domain.models.Result
import com.example.yandexsummerschool.domain.useCases.account.GetAccountUseCase
import com.example.yandexsummerschool.domain.useCases.incomes.GetIncomesUseCase
import com.example.yandexsummerschool.domain.utils.calculateSum
import com.example.yandexsummerschool.domain.utils.date.millsToIsoDateSimple
import com.example.yandexsummerschool.ui.common.BaseViewModel
import com.example.yandexsummerschool.ui.common.ErrorMessageResolver
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel для экрана доходов. Загружает и хранит состояние доходов [IncomesScreenState].
 */
class IncomesScreenViewModel @Inject constructor(
    private val getIncomesUseCase: GetIncomesUseCase,
    override val userDelegate: UserDelegate,
    override val getAccountUseCase: GetAccountUseCase,
) : BaseViewModel() {
    private var fetchJob: Job? = null
    private val _incomeState = MutableStateFlow<IncomesScreenState>(IncomesScreenState.Loading)
    val incomesState: StateFlow<IncomesScreenState> = _incomeState

    init {
        loadIncomes()
    }

    fun loadIncomes() {
        fetchJob?.cancel()
        fetchJob =
            viewModelScope.launch {
                val today = millsToIsoDateSimple(System.currentTimeMillis())
                when (
                    val result =
                        getIncomesUseCase(
                            accountId = getAccountId(),
                            startDate = today,
                            endDate = today,
                        )
                ) {
                    is Result.Success -> {
                        val incomes = result.data
                        if (incomes.isEmpty()) {
                            _incomeState.value = IncomesScreenState.Empty
                        } else {
                            val sum = calculateSum(incomes)
                            _incomeState.value = IncomesScreenState.Content(incomes, sum)
                        }
                    }

                    is Result.Failure -> {
                        _incomeState.value = IncomesScreenState.Error(ErrorMessageResolver.resolve(result.exception))
                    }
                }
            }
    }
}
