package app.isfa.spendings.data.repository

import app.isfa.spendings.data.api.EndpointApi
import app.isfa.spendings.data.entity.GeminiResponse
import app.isfa.spendings.data.repository.request.GeminiRequestBody

interface GeminiRepository {

    suspend fun request(prompt: String, image: ByteArray): Result<String>
}

internal class GeminiRepositoryImpl(private val api: EndpointApi) : GeminiRepository {

    override suspend fun request(prompt: String, image: ByteArray): Result<String> {
        val reqBody = GeminiRequestBody.create(prompt, image)
        return api.extractInfo(reqBody).map(::extractFirstPromptResult)
    }

    private fun extractFirstPromptResult(response: GeminiResponse) =
        response.candidates
            .firstOrNull()
            ?.content
            ?.parts
            ?.firstOrNull()
            ?.text
            .orEmpty()

}