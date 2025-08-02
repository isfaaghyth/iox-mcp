package app.isfa.spendings.robot

import app.isfa.spendings.domain.GetExpenseInfoUseCase
import app.isfa.spendings.fake.GeminiRepositoryFake
import app.isfa.spendings.fake.PromptRepositoryFake

object GetExpenseInfoUseCaseRobot {

    private val geminiRepository = GeminiRepositoryFake()
    private val promptRepository = PromptRepositoryFake()

    fun extractable(name: String) =
        get(
            Result.success(
                """
                    {
                        "name": "$name",
                        "total_rupiah": "50000",
                        "category": "Food and Beverage",
                        "time": "0"
                    }
                """.trimIndent()
            )
        )

    fun unextractable() =
        get(
            Result.success(
                """
                    {
                        "name": "Coffee Shop",
                        "total": "50000",
                        "time": "0"
                    }
                """.trimIndent()
            )
        )

    fun failure() = get(Result.failure(Exception("")))

    fun get(geminiResult: Result<String>): GetExpenseInfoUseCase {
        geminiRepository.result = geminiResult

        return GetExpenseInfoUseCase(
            geminiRepository = geminiRepository,
            promptRepository = promptRepository
        )
    }
}