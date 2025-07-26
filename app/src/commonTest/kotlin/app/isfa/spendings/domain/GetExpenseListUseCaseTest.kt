package app.isfa.spendings.domain

import app.isfa.spendings.data.entity.ExpenseResponse
import app.isfa.spendings.fake.ExpenseRepositoryFake
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertTrue

class GetExpenseListUseCaseTest {

    @Test
    fun `the expense list service should return list of expenses`() {
        runBlocking {
            val expenses = listOf(
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

            val response = useCase(Result.success(expenses))
            val result = response.fetch().first()

            assertTrue { result != null }
            assertTrue { !result.isNullOrEmpty() }
        }
    }

    @Test
    fun `the expense list service should return an empty list`() {
        runBlocking {
            val expenses = listOf<ExpenseResponse>()

            val response = useCase(Result.success(expenses))
            val result = response.fetch().first()

            assertTrue { result != null }
            assertTrue { result.isNullOrEmpty() }
        }
    }

    private fun useCase(data: Result<List<ExpenseResponse>>): GetExpenseListUseCase {
        return GetExpenseListUseCase(
            repository = ExpenseRepositoryFake().apply {
                result = data
            }
        )
    }
}