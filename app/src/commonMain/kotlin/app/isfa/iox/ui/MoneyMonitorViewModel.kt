package app.isfa.iox.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.isfa.iox.domain.GetGeminiUseCase
import app.isfa.iox.intent.ImageIntentData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MoneyMonitorViewModel(private val useCase: GetGeminiUseCase) : ViewModel() {

    private val _uiState = MutableStateFlow(MoneyMonitorUiState.Default)
    val uiState get() = _uiState.asStateFlow()

    private val event = MutableSharedFlow<MoneyMonitorEvent>(replay = 50)

    init {
        viewModelScope.launch {
            event
                .distinctUntilChanged()
                .collect(::observeUserEvent)
        }
    }

    fun sendEvent(action: MoneyMonitorEvent) {
        event.tryEmit(action)
    }

    private fun observeUserEvent(event: MoneyMonitorEvent) {
        when (event) {
            is SendRequest -> shouldGeminiRequest(event.model)
        }
    }

    private fun shouldGeminiRequest(model: ImageIntentData) {
        viewModelScope.launch(Dispatchers.IO) {
            val request = useCase(model.imageData)

            withContext(Dispatchers.Main) {
                _uiState.update {
                    it.copy(result = request.toString())
                }
            }
        }
    }
}