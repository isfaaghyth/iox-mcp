package app.isfa.spendings

import app.isfa.spendings.domain.model.ExpenseUiModel
import app.isfa.spendings.intent.ImageIntentData

sealed interface MainAction

// Effects
data object HideToast : MainAction
data class IntentProceed(val data: ImageIntentData?) : MainAction

// Events
data class SaveExpense(val model: ExpenseUiModel) : MainAction
data object DismissExpense : MainAction