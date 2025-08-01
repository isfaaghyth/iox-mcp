@file:OptIn(ExperimentalTime::class)

package app.isfa.spendings.domain

import app.isfa.spendings.data.entity.ExpenseResponse
import app.isfa.spendings.data.repository.ExpenseRepository
import app.isfa.spendings.data.repository.request.ExpenseRequestBody
import app.isfa.spendings.domain.model.ExpenseUiModel
import app.isfa.spendings.domain.model.GroupExpenseUiModel
import app.isfa.spendings.util.getDayOfWeekShort
import app.isfa.spendings.util.getMonthShort
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

class GetExpenseListUseCase(private val repository: ExpenseRepository) {

    fun fetch(): Flow<Result<List<GroupExpenseUiModel>>> {
        return flow {
            val result = repository.all()
                .map { it.map(::transformToUiModel) }
                .map { it.groupByDate() }

            emit(result)
        }
    }

    suspend fun store(body: ExpenseRequestBody) {
        repository.create(body)
    }

    private fun List<ExpenseUiModel>.groupByDate(): List<GroupExpenseUiModel> {
        val today = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
        val yesterday = today.minus(1, DateTimeUnit.DAY)

        fun readableDate(date: LocalDate): String {
            return when (date) {
                today -> "Hari Ini"
                yesterday -> "Kemarin"
                else -> "${getDayOfWeekShort(date.dayOfWeek)}, ${getMonthShort(date.month)} ${date.day}"
            }
        }

        return this
            .groupBy { it.toLocalDateTime().date }
            .map { (date, expenses) ->
                GroupExpenseUiModel(
                    date = readableDate(date),
                    items = expenses,
                    total = expenses.sumOf { it.amount.toLong() }
                )
            }.sortedBy { it.date }
    }

    private fun transformToUiModel(response: ExpenseResponse) =
        ExpenseUiModel(
            name = response.name,
            amount = response.amount.toIntOrNull() ?: 0,
            category = response.category,
            time = response.time.toLongOrNull() ?: 0
        )
}