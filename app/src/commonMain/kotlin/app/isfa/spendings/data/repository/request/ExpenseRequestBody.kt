package app.isfa.spendings.data.repository.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class ExpenseRequestBody(
    @SerialName("name") val name: String,
    @SerialName("amount") val amount: String,
    @SerialName("category") val category: String,
    @SerialName("time") val time: String
)