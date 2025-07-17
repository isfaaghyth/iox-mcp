package app.isfa.iox.domain

import app.isfa.iox.data.entity.GeminiResponse
import app.isfa.iox.data.repository.GeminiRepository

class GetGeminiUseCase(private val repository: GeminiRepository) {

    suspend operator fun invoke(prompt: String, image: ByteArray): String? {
        return repository.request(prompt, image)
            .map(::extractFirstPromptResult)
            .getOrNull()
    }

    private fun extractFirstPromptResult(response: GeminiResponse) =
        response.candidates
            .firstOrNull()
            ?.content
            ?.parts
            ?.firstOrNull()
            ?.text
}