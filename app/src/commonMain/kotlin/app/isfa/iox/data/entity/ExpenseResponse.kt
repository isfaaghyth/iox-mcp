package app.isfa.iox.data.entity

import kotlinx.serialization.Serializable

@Serializable
data class ExpenseResponse(
    val id: Int,
    val name: String,
    val amount: String,
    val category: String,
    val time: String
)