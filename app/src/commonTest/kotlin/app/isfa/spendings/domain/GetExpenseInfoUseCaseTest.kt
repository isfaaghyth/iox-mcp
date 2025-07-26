package app.isfa.spendings.domain

import app.isfa.spendings.fake.GeminiRepositoryFake
import app.isfa.spendings.fake.PromptRepositoryFake
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertTrue

class GetExpenseInfoUseCaseTest {

    private val geminiRepository = GeminiRepositoryFake()
    private val promptRepository = PromptRepositoryFake()

    @Test
    fun `the receipt extractor with gemini returns valid data`() {
        runBlocking {
            val infoExtracted = """
                {
                    "name": "Coffee Shop",
                    "total_rupiah": "50000",
                    "category": "Food and Beverage",
                    "time": "0"
                }
            """.trimIndent()

            val result = useCase(Result.success(infoExtracted))

            assertTrue { result.invoke(null) != null }
            assertTrue { result.invoke(null)?.name == "Coffee Shop" }
        }
    }

    @Test
    fun `the receipt extractor with gemini fails to parse the json`() {
        runBlocking {
            val infoExtracted = """
                {
                    "name": "Coffee Shop",
                    "total": "50000",
                    "time": "0"
                }
            """.trimIndent()

            val result = useCase(Result.success(infoExtracted))
            assertTrue { result.invoke(null) == null }
        }
    }

    @Test
    fun `the receipt extractor with gemini fails to extract information`() {
        runBlocking {
            val result = useCase(Result.failure(Exception("")))
            assertTrue { result.invoke(null) == null }
        }
    }

    private fun useCase(
        geminiResult: Result<String>
    ): GetExpenseInfoUseCase {
        geminiRepository.result = geminiResult

        return GetExpenseInfoUseCase(
            geminiRepository = geminiRepository,
            promptRepository = promptRepository
        )
    }
}