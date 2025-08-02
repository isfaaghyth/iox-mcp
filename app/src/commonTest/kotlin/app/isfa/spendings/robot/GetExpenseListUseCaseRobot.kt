package app.isfa.spendings.robot

import app.isfa.spendings.data.entity.ExpenseResponse
import app.isfa.spendings.domain.GetExpenseListUseCase
import app.isfa.spendings.fake.ExpenseRepositoryFake

object GetExpenseListUseCaseRobot {

    fun expenses() =
        get(
            Result.success(
                listOf(
                    ExpenseResponse(
                        id = 1,
                        name = "Loren",
                        amount = "1000",
                        category = "abc",
                        time = "0"
                    ),
                    ExpenseResponse(
                        id = 2,
                        name = "Ipsum",
                        amount = "1000",
                        category = "abc",
                        time = "0"
                    )
                )
            )
        )

    fun empty() = get(Result.success(listOf()))

    fun failure() = get(Result.failure(Throwable("")))

    fun get(data: Result<List<ExpenseResponse>>) =
        GetExpenseListUseCase(
            repository = ExpenseRepositoryFake().apply {
                result = data
            }
        )
}