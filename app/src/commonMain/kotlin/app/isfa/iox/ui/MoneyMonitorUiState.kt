package app.isfa.iox.ui

data class MoneyMonitorUiState(
    val result: String,
    val state: GeminiState
) {

    companion object {
        val Default get() = MoneyMonitorUiState(
            result = "",
            state = GeminiState.Loading
        )
    }
}

sealed interface GeminiState {
    data object Loading : GeminiState
    data object Error : GeminiState
    data object Succeed : GeminiState
}