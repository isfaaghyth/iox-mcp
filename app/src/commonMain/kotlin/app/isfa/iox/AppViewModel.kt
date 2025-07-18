package app.isfa.iox

import app.isfa.iox.domain.GetExpenseInfoUseCase
import app.isfa.iox.domain.GetExpenseListUseCase
import app.isfa.iox.intent.ImageIntentData
import app.isfa.iox.intent.ImageIntentDataPublisher
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class AppViewModel(
    private val expenseInfoUseCase: GetExpenseInfoUseCase,
    private val expenseListUseCase: GetExpenseListUseCase
) : ScreenModel {

    private var intentData = MutableSharedFlow<ImageIntentData>(replay = 1)

    val intentState = intentData
        .distinctUntilChanged()
        .map { expenseInfoUseCase(it.imageData) }
        .stateIn(
            scope = screenModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )

    val expenseListState get() = expenseListUseCase()
        .stateIn(
            scope = screenModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = null
        )

    fun emit(data: ImageIntentData?) {
        if (data == null) return

        intentData.tryEmit(data)

        // reset from global intent publisher
        ImageIntentDataPublisher.reset()
    }
}