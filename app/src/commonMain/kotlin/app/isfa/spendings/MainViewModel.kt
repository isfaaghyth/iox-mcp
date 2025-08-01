package app.isfa.spendings

import app.isfa.spendings.data.repository.request.ExpenseRequestBody
import app.isfa.spendings.domain.GetExpenseInfoUseCase
import app.isfa.spendings.domain.GetExpenseListUseCase
import app.isfa.spendings.domain.model.ExpenseUiModel
import app.isfa.spendings.intent.ImageIntentData
import app.isfa.spendings.intent.ImageIntentDataPublisher
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel(
    private val expenseInfoUseCase: GetExpenseInfoUseCase,
    private val expenseListUseCase: GetExpenseListUseCase
) : ScreenModel {

    private val _state = MutableStateFlow(MainUiState.Default)
    val state get() = _state.asStateFlow()

    private var _action = MutableSharedFlow<MainAction>(replay = 2)

    init {
        fetchExpenseList()

        screenModelScope.launch(Dispatchers.Default) {
            _action
                .distinctUntilChanged()
                .collect(::onActionHandler)
        }
    }

    fun sendAction(action: MainAction) {
        _action.tryEmit(action)
    }

    private fun onActionHandler(action: MainAction) {
        when (action) {
            DismissExpense -> removeCurrentIntentData()
            HideToast -> hideToast()
            is IntentProceed -> intentImageProceed(action.data)
            is SaveExpense -> storeExpenseData(action.model)
        }
    }

    private fun intentImageProceed(data: ImageIntentData? = null) {
        if (data == null) return

        _state.update {
            it.copy(
                isIntentProceed = true,
                currentIntentData = data
            )
        }

        extractReceiptWithGemini(data)
        ImageIntentDataPublisher.reset()
    }

    private fun storeExpenseData(model: ExpenseUiModel?) {
        if (model == null) return

        val body = ExpenseRequestBody(
            name = model.name,
            amount = model.amount.toString(),
            category = model.category,
            time = model.time.toString()
        )

        screenModelScope.launch {
            expenseListUseCase.store(body)
            fetchExpenseList() // Refresh list
        }
    }

    private fun hideToast() = _state.update { it.copy(errorMessage = "") }

    private fun removeCurrentIntentData() {
        _state.update {
            it.copy(
                currentIntentData = null,
                intentDataProceed = null
            )
        }

        ImageIntentDataPublisher.reset()
    }

    private fun fetchExpenseList() {
        screenModelScope.launch(Dispatchers.IO) {
            expenseListUseCase.fetch()
                .collect { expenseList ->
                    expenseList
                        .onSuccess { result ->
                            _state.update { it.copy(expanseList = result) }
                        }
                        .onFailure { error ->
                            _state.update {
                                it.copy(errorMessage = error.message.orEmpty())
                            }
                        }
                }
        }
    }

    private fun extractReceiptWithGemini(data: ImageIntentData) {
        screenModelScope.launch(Dispatchers.IO) {
            expenseInfoUseCase(data.imageData)
                .onSuccess { result ->
                    _state.update {
                        it.copy(
                            intentDataProceed = result,
                            isIntentProceed = false
                        )
                    }
                }
                .onFailure { error ->
                    _state.update {
                        it.copy(errorMessage = error.message.orEmpty())
                    }
                }
        }
    }
}