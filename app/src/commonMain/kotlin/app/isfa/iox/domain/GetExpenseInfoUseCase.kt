package app.isfa.iox.domain

import app.isfa.iox.data.repository.GeminiRepository
import app.isfa.iox.data.repository.PromptRepository
import app.isfa.iox.data.repository.prompt.PromptFile
import app.isfa.iox.data.repository.prompt.ReceiptExtractor
import app.isfa.iox.domain.model.ExpenseUiModel
import app.isfa.iox.domain.model.ReceiptExtractorModel
import app.isfa.iox.util.cleanUp
import kotlinx.serialization.json.Json

class GetExpenseInfoUseCase(
    private val geminiRepository: GeminiRepository,
    private val promptRepository: PromptRepository
) {

    suspend operator fun invoke(image: ByteArray): ExpenseUiModel? {
        val prompt = promptRepository.prompt(PromptFile.ReceiptExtractor)

        return geminiRepository
            .request(prompt, image)
            .map { it.cleanUp() } // if gemini returns as a json markdown format, remove it.
            .map{ Json.decodeFromString<ReceiptExtractorModel?>(it) }
            .map(::transformToUiModel)
            .getOrNull()
    }

    private fun transformToUiModel(model: ReceiptExtractorModel?): ExpenseUiModel {
        if (model == null) return ExpenseUiModel.Empty

        return ExpenseUiModel(
            name = model.name,
            amount = model.total.toInt(),
            category = model.category,
            time = model.time
        )
    }
}