package app.isfa.iox.domain

import app.isfa.iox.domain.model.ExpenseUiModel
import app.isfa.iox.domain.model.GroupExpenseUiModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetExpenseListUseCase {

    operator fun invoke(): Flow<List<GroupExpenseUiModel>?> {
        return flow {
            emit(
                listOf(
                    GroupExpenseUiModel(
                        date = "Hari Ini",
                        items = listOf(
                            ExpenseUiModel(
                                name = "Tokopedia",
                                amount = 123,
                                category = "Shopping",
                                time = 1557486767000
                            ),
                            ExpenseUiModel(
                                name = "Tokopedia",
                                amount = 123,
                                category = "Shopping",
                                time = 1557486767000
                            ),
                            ExpenseUiModel(
                                name = "Tokopedia",
                                amount = 123,
                                category = "Shopping",
                                time = 1557486767000
                            )
                        )
                    ),
                    GroupExpenseUiModel(
                        date = "Kemarin",
                        items = listOf(
                            ExpenseUiModel(
                                name = "Tokopedia",
                                amount = 123,
                                category = "Shopping",
                                time = 1557486767000
                            ),
                            ExpenseUiModel(
                                name = "Tokopedia",
                                amount = 123,
                                category = "Shopping",
                                time = 1557486767000
                            ),
                            ExpenseUiModel(
                                name = "Tokopedia",
                                amount = 123,
                                category = "Shopping",
                                time = 1557486767000
                            )
                        )
                    )
                )
            )
        }
    }
}