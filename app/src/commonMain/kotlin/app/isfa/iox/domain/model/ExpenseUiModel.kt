@file:OptIn(ExperimentalTime::class)

package app.isfa.iox.domain.model

import app.isfa.iox.util.formatReadableDateTime
import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

data class ExpenseUiModel(
    val merchantName: String,
    val amount: Int,
    val category: String,
    val time: Long
) {

    fun asReadableTime() = time.formatReadableDateTime()

    companion object {
        val Empty get() = ExpenseUiModel(
            merchantName = "",
            amount = 0,
            category = "",
            time = 0
        )
    }
}