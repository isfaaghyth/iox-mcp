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

    private var intentData = MutableSharedFlow<ImageIntentData>(replay = 1)

    private val _state = MutableStateFlow(MainUiState.Default)
    val state get() = _state.asStateFlow()

    init {
        refreshExpanseList()

        screenModelScope.launch(Dispatchers.Default) {
            intentData
                .distinctUntilChanged()
                .collect(::shouldImageIntentDataProceed)
        }
    }

    fun refreshExpanseList() {
        screenModelScope.launch(Dispatchers.IO) {
            expenseListUseCase.fetch().collect { result ->
                _state.update { it.copy(expanseList = result) }
            }
        }
    }

    private fun shouldImageIntentDataProceed(data: ImageIntentData) {
        screenModelScope.launch(Dispatchers.IO) {
            val result = expenseInfoUseCase(data.imageData)

            _state.update {
                it.copy(
                    intentDataProceed = result,
                    isIntentProceed = false
                )
            }
        }
    }

    fun setIntentImageData(data: ImageIntentData? = null) {
        if (data == null) return

        _state.update { it.copy(isIntentProceed = true) }
        intentData.tryEmit(data)
        ImageIntentDataPublisher.reset()
    }

    fun saveData(model: ExpenseUiModel?) {
        if (model == null) return

        refreshExpanseList()

        val body = ExpenseRequestBody(
            name = model.name,
            amount = model.amount.toString(),
            category = model.category,
            time = model.time.toString()
        )

        screenModelScope.launch {
            expenseListUseCase.store(body)
        }
    }
}