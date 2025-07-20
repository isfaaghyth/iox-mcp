package app.isfa.spendings.domain

import app.isfa.spendings.data.entity.ExpenseResponse
import app.isfa.spendings.data.repository.ExpenseRepository
import app.isfa.spendings.data.repository.request.ExpenseRequestBody
import app.isfa.spendings.domain.model.ExpenseUiModel
import app.isfa.spendings.domain.model.GroupExpenseUiModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetExpenseListUseCase(private val repository: ExpenseRepository) {

    operator fun invoke(): Flow<List<GroupExpenseUiModel>?> {
        return flow {
            val result = repository
                .all(fromMcp = false)
                .getOrDefault(emptyList())

            emit(
                if (result.isNotEmpty()) {
                    listOf(
                        GroupExpenseUiModel(
                            date = "Hari Ini",
                            items = result.map(::transformToUiModel)
                        ),
                    )
                } else {
                    emptyList()
                }
            )
        }
    }

    suspend fun store(body: ExpenseRequestBody) {
        repository.create(body)
    }

    private fun transformToUiModel(response: ExpenseResponse) =
        ExpenseUiModel(
            name = response.name,
            amount = response.amount.toIntOrNull() ?: 0,
            category = response.category,
            time = response.time.toLongOrNull() ?: 0
        )
}