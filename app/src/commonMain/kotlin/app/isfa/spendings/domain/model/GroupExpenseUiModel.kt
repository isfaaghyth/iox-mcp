package app.isfa.spendings.domain.model

import app.isfa.spendings.util.formatDecimalSeparator

data class GroupExpenseUiModel(
    val date: String,
    val items: List<ExpenseUiModel>,
    val total: Long? = null
) {

    val totalFormatted: String
        get() = total
            ?.formatDecimalSeparator()
            .orEmpty()
            .ifEmpty { "0" }
}