package app.isfa.iox.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.isfa.iox.domain.GetGeminiUseCase
import app.isfa.iox.intent.ImageIntentData
import app.isfa.iox.util.cleanUp
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
        val prompt = """
                You are a receipt information extractor. Extract the following information from the attached receipt image:
                
                1. Total amount: Find the exact total amount in Indonesian Rupiah (IDR)
                2. Merchant name: Identify the business/store name
                3. Category: Determine the most appropriate spending category based on the merchant and items purchased
                
                Return only a valid JSON response in this exact format:
                { "merchant_name": "", "total_rupiah": "", "category": "" }
                
                Requirements:
                - Extract the exact total amount as it appears on the receipt
                - Use the full merchant name as shown on the receipt
                - Select an appropriate category (e.g., "Food & Dining", "Groceries", "Transportation", "Shopping", "Healthcare", etc.)
                - Return only the JSON object, no additional text or explanations
            """.trimIndent()

        viewModelScope.launch(Dispatchers.IO) {
            val request = useCase(prompt, model.imageData)

            withContext(Dispatchers.Main) {
                _uiState.update {
                    it.copy(
                        result = request.orEmpty().cleanUp(),
                        state = if (!request.isNullOrEmpty()) GeminiState.Succeed else GeminiState.Error
                    )
                }
            }
        }
    }
}