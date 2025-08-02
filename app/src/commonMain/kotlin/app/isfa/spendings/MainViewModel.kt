package app.isfa.spendings

import app.isfa.spendings.domain.GetExpenseInfoUseCase
import app.isfa.spendings.domain.GetExpenseListUseCase
import app.isfa.spendings.domain.model.ExpenseUiModel
import app.isfa.spendings.intent.ImageIntentData
import app.isfa.spendings.intent.ImageIntentDataPublisher
import app.isfa.spendings.intent.ImageIntentState
import app.isfa.spendings.util.CoroutineDispatchers
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel(
    private val expenseInfoUseCase: GetExpenseInfoUseCase,
    private val expenseListUseCase: GetExpenseListUseCase,
    private val dispatchers: CoroutineDispatchers
) : ScreenModel {

    private val _state = MutableStateFlow(MainUiState.Default)
    val state get() = _state.asStateFlow()

    private var _action = MutableSharedFlow<MainAction>(replay = 2)

    init {
        fetchExpenseList()

        screenModelScope.launch(dispatchers.default) {
            ImageIntentDataPublisher.capturedImage
                .collect(::onImageIntent)
        }

        screenModelScope.launch(dispatchers.default) {
            _action
                .distinctUntilChanged()
                .collect(::onActionHandler)
        }
    }

    fun sendAction(action: MainAction) {
        _action.tryEmit(action)
    }

    private fun onImageIntent(state: ImageIntentState) {
        when (state) {
            is ImageIntentState.Succeed -> intentImageProceed(state.data)
            is ImageIntentState.Loading -> loadingState()
            is ImageIntentState.Fail -> showToast(Throwable("Fail to retrieve the image's data"))
            is ImageIntentState.None -> Unit
        }
    }

    private fun onActionHandler(action: MainAction) {
        when (action) {
            is SaveExpense -> storeExpenseData(action.model)
            DismissExpense -> removeCurrentIntentData()
            HideToast -> hideToast()
        }
    }

    private fun intentImageProceed(data: ImageIntentData? = null) {
        if (data == null) return

        _state.update { it.copy(currentIntentData = data) }
        extractReceiptWithGemini(data)
    }

    private fun storeExpenseData(model: ExpenseUiModel?) {
        if (model == null) return

        screenModelScope.launch {
            expenseListUseCase.store(model.asRequestBody())
            fetchExpenseList() // refresh list
        }
    }

    private fun fetchExpenseList() {
        screenModelScope.launch(dispatchers.io) {
            expenseListUseCase.fetch()
                .collect { expenseList ->
                    expenseList
                        .onSuccess { result ->
                            _state.update { it.copy(expanseList = result) }
                        }
                        .onFailure(::showToast)
                }
        }
    }

    private fun extractReceiptWithGemini(data: ImageIntentData) {
        screenModelScope.launch(dispatchers.io) {
            expenseInfoUseCase(data.imageData)
                .onSuccess { result ->
                    _state.update {
                        it.copy(
                            intentDataProceed = result,
                            isIntentProceed = false
                        )
                    }
                }
                .onFailure(::showToast)
        }
    }

    private fun loadingState() = _state.update { it.copy(isIntentProceed = true) }

    private fun showToast(t: Throwable) {
        val errorMessage = t.message.orEmpty()

        // render the explicit error message in logcat
        println(errorMessage)

        // show toast error message
        _state.update { it.copy(errorMessage = "Uh-no! Please try again.") }
    }

    private fun hideToast() = _state.update { it.copy(errorMessage = "") }

    private fun removeCurrentIntentData() {
        _state.update { it.resetIntentData() }
        ImageIntentDataPublisher.reset()
    }
}