package app.isfa.iox.domain

import app.isfa.iox.data.repository.GeminiRepository
import app.isfa.iox.domain.model.ReceiptExtractorModel
import kotlinx.serialization.json.Json

class GetGeminiUseCase(private val repository: GeminiRepository) {

    suspend operator fun invoke(image: ByteArray): ReceiptExtractorModel? {
        val result = repository.request(image) ?: return null
        return Json.decodeFromString(result)
    }
}