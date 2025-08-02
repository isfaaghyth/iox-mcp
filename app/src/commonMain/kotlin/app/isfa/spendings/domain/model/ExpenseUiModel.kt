@file:OptIn(ExperimentalTime::class)

package app.isfa.spendings.domain.model

import app.isfa.spendings.data.repository.request.ExpenseRequestBody
import app.isfa.spendings.util.formatDecimalSeparator
import app.isfa.spendings.util.convertToLocalDateTime
import app.isfa.spendings.util.formatReadableDateTime
import kotlin.time.ExperimentalTime

data class ExpenseUiModel(
    val name: String,
    val amount: Int,
    val category: String,
    val time: Long
) {

    val amountFormatted: String
        get() = amount.formatDecimalSeparator()

    fun toLocalDateTime() = time.convertToLocalDateTime()

    fun toReadableTime() = toLocalDateTime().formatReadableDateTime()

    fun asRequestBody() = ExpenseRequestBody(
        name = name,
        amount = amount.toString(),
        category = category,
        time = time.toString()
    )

    companion object {
        val Empty get() = ExpenseUiModel(
            name = "",
            amount = 0,
            category = "",
            time = 0
        )
    }
}