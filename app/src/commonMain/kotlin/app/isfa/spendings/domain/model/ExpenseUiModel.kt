@file:OptIn(ExperimentalTime::class)

package app.isfa.spendings.domain.model

import app.isfa.spendings.util.convertToLocalDateTime
import app.isfa.spendings.util.formatReadableDateTime
import kotlin.time.ExperimentalTime

data class ExpenseUiModel(
    val name: String,
    val amount: Int,
    val category: String,
    val time: Long
) {

    fun toLocalDateTime() = time.convertToLocalDateTime()

    fun toReadableTime() = toLocalDateTime().formatReadableDateTime()

    companion object {
        val Empty get() = ExpenseUiModel(
            name = "",
            amount = 0,
            category = "",
            time = 0
        )
    }
}