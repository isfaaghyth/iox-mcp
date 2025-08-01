package app.isfa.spendings.domain

import app.isfa.spendings.data.repository.GeminiRepository
import app.isfa.spendings.data.repository.PromptRepository
import app.isfa.spendings.data.repository.prompt.PromptFile
import app.isfa.spendings.data.repository.prompt.ReceiptExtractor
import app.isfa.spendings.domain.model.ExpenseUiModel
import app.isfa.spendings.domain.model.ReceiptExtractorModel
import kotlinx.serialization.json.Json

class GetExpenseInfoUseCase(
    private val geminiRepository: GeminiRepository,
    private val promptRepository: PromptRepository
) {

    suspend operator fun invoke(image: ByteArray?): Result<ExpenseUiModel?> {
        val prompt = promptRepository.read(PromptFile.ReceiptExtractor)

        return geminiRepository
            .request(prompt, image)
            // if gemini returns as a json markdown format, remove it.
            .map { it.toExactJson() }
            .map(::transform)
    }

    private fun transform(result: String): ExpenseUiModel? {
        return try {
            val resultCleanedUp = result.toExactJson()

            val model = Json.decodeFromString<ReceiptExtractorModel?>(resultCleanedUp)
                ?: return ExpenseUiModel.Empty

            ExpenseUiModel(
                name = model.name,
                amount = model.total.toInt(),
                category = model.category,
                time = model.time
            )
        } catch (e: Exception) {
            null
        }
    }

    // Somehow gemini returns as json markdown format, this mapper will remove unexpected json format.
    private fun String.toExactJson(): String {
        if (isEmpty()) return ""

        return this
            .replace("```json", "")
            .replace("```", "")
            .replace("\\n", "")
            .replace("\\", "")
            .trim()
    }
}