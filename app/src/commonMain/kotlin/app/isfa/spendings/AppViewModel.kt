package app.isfa.spendings

import app.isfa.spendings.data.repository.request.ExpenseRequestBody
import app.isfa.spendings.domain.GetExpenseInfoUseCase
import app.isfa.spendings.domain.GetExpenseListUseCase
import app.isfa.spendings.domain.model.ExpenseUiModel
import app.isfa.spendings.domain.model.GroupExpenseUiModel
import app.isfa.spendings.intent.ImageIntentData
import app.isfa.spendings.intent.ImageIntentDataPublisher
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class AppViewModel(
    private val expenseInfoUseCase: GetExpenseInfoUseCase,
    private val expenseListUseCase: GetExpenseListUseCase
) : ScreenModel {

    private var intentData = MutableSharedFlow<ImageIntentData>(replay = 1)

    private var _expenseListState = MutableStateFlow<List<GroupExpenseUiModel>?>(null)
    val expenseListState get() = _expenseListState.asStateFlow()

    val intentState = intentData
        .distinctUntilChanged()
        .map { expenseInfoUseCase(it.imageData) }
        .stateIn(
            scope = screenModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )

    init {
        fetchExpenseList()
    }

    private fun fetchExpenseList() {
        screenModelScope.launch {
            expenseListUseCase.fetch().collect {
                _expenseListState.value = it
            }
        }
    }

    fun emit(data: ImageIntentData?) {
        if (data == null) return

        intentData.tryEmit(data)

        // reset from global intent publisher
        ImageIntentDataPublisher.reset()
    }

    fun store(model: ExpenseUiModel) {
        val asRequestBody = ExpenseRequestBody(
            name = model.name,
            amount = model.amount.toString(),
            category = model.category,
            time = model.time.toString()
        )

        screenModelScope.launch {
            expenseListUseCase.store(asRequestBody)
        }

        // refresh
        fetchExpenseList()
    }
}