package app.isfa.spendings

import app.isfa.spendings.domain.model.ExpenseUiModel
import app.isfa.spendings.domain.model.GroupExpenseUiModel
import app.isfa.spendings.intent.ImageIntentData

data class MainUiState(
    val currentIntentData: ImageIntentData?,
    val intentDataProceed: ExpenseUiModel?,
    val expanseList: List<GroupExpenseUiModel>?,
    val isIntentProceed: Boolean
) {

    companion object {
        val Default get() = MainUiState(
            currentIntentData = null,
            intentDataProceed = null,
            expanseList = emptyList(),
            isIntentProceed = false
        )
    }
}