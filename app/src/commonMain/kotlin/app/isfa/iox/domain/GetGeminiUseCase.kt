package app.isfa.iox.domain

import app.isfa.iox.data.repository.GeminiRepository
import app.isfa.iox.data.repository.PromptRepository
import app.isfa.iox.data.repository.prompt.PromptFile
import app.isfa.iox.data.repository.prompt.ReceiptExtractor
import app.isfa.iox.domain.model.ReceiptExtractorModel
import app.isfa.iox.util.cleanUp
import kotlinx.serialization.json.Json

class GetGeminiUseCase(
    private val geminiRepository: GeminiRepository,
    private val promptRepository: PromptRepository
) {

    suspend operator fun invoke(image: ByteArray): Result<ReceiptExtractorModel?> {
        val prompt = promptRepository.prompt(PromptFile.ReceiptExtractor)

        return geminiRepository
            .request(prompt, image)
            .map { it.cleanUp() }
            .map { Json.decodeFromString(it) }
    }
}