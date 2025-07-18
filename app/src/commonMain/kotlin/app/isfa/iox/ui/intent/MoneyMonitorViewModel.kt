package app.isfa.iox.ui.intent

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.isfa.iox.domain.GetGeminiUseCase
import app.isfa.iox.intent.ImageIntentData
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class MoneyMonitorViewModel(private val useCase: GetGeminiUseCase) : ViewModel() {

    private var intentData = MutableSharedFlow<ImageIntentData>(replay = 1)

    val state = intentData
        .distinctUntilChanged()
        .map { useCase(it.imageData) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = Result.success(null)
        )

    fun emit(data: ImageIntentData) {
        intentData.tryEmit(data)
    }
}