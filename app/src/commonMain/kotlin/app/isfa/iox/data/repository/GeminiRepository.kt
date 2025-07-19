package app.isfa.iox.data.repository

import app.isfa.iox.data.api.EndpointApi
import app.isfa.iox.data.entity.GeminiResponse
import app.isfa.iox.data.repository.request.GeminiRequestBody

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