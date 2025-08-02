package app.isfa.spendings

import app.isfa.spendings.domain.model.ExpenseUiModel

sealed interface MainAction

// Effects
data object HideToast : MainAction

// Events
data class SaveExpense(val model: ExpenseUiModel) : MainAction
data object DismissExpense : MainAction