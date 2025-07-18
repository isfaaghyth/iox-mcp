package app.isfa.iox.domain.model

data class GroupExpenseUiModel(
    val date: String,
    val items: List<ExpenseUiModel>,
    val total: Long? = null
)