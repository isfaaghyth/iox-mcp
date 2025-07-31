package app.isfa.spendings

import app.isfa.spendings.domain.model.ExpenseUiModel
import app.isfa.spendings.domain.model.GroupExpenseUiModel
import app.isfa.spendings.intent.ImageIntentData

data class MainUiState(
    val currentIntentData: ImageIntentData?,
    val intentDataProceed: ExpenseUiModel?,
    val expanseList: List<GroupExpenseUiModel>?,
    val errorMessage: String,
    val isIntentProceed: Boolean
) {

    fun intentProceed() = isIntentProceed && errorMessage.isEmpty()

    companion object {
        val Default get() = MainUiState(
            currentIntentData = null,
            intentDataProceed = null,
            expanseList = emptyList(),
            errorMessage = "",
            isIntentProceed = false
        )
    }
}