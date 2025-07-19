@file:OptIn(ExperimentalTime::class)

package app.isfa.iox.domain.model

import app.isfa.iox.util.formatReadableDateTime
import kotlin.time.ExperimentalTime

data class ExpenseUiModel(
    val name: String,
    val amount: Int,
    val category: String,
    val time: Long
) {

    fun asReadableTime() = time.formatReadableDateTime()

    companion object {
        val Empty get() = ExpenseUiModel(
            name = "",
            amount = 0,
            category = "",
            time = 0
        )
    }
}