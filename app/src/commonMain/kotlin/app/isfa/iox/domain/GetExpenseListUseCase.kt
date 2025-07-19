package app.isfa.iox.domain

import app.isfa.iox.data.repository.ExpenseRepository
import app.isfa.iox.data.repository.request.ExpenseRequestBody
import app.isfa.iox.domain.model.ExpenseUiModel
import app.isfa.iox.domain.model.GroupExpenseUiModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetExpenseListUseCase(private val repository: ExpenseRepository) {

    operator fun invoke(): Flow<List<GroupExpenseUiModel>?> {
        return flow {
            val result = repository.all(fromMcp = false).getOrDefault(emptyList())

            emit(
                listOf(
                    GroupExpenseUiModel(
                        date = "Hari Ini",
                        items = result.map {
                            ExpenseUiModel(
                                name = it.name,
                                amount = it.amount.toIntOrNull() ?: 0,
                                category = it.category,
                                time = it.time.toLongOrNull() ?: 0
                            )
                        }
                    ),
                )
            )
        }
    }

    suspend fun store(body: ExpenseRequestBody) {
        repository.create(body)
    }
}