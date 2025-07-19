package app.isfa.iox.data.repository.request

import kotlinx.serialization.Serializable

@Serializable
class ExpenseRequestBody(
    val name: String,
    val amount: String,
    val category: String,
    val time: String
)