package app.isfa.iox.data.repository

import app.isfa.iox.data.api.GeminiApi
import app.isfa.iox.data.entity.GeminiResponse
import app.isfa.iox.data.repository.request.RequestBody
import app.isfa.iox.util.cleanUp

interface GeminiRepository {

    suspend fun request(prompt: String, image: ByteArray): Result<String>
}

internal class GeminiRepositoryImpl(private val api: GeminiApi) : GeminiRepository {

    override suspend fun request(prompt: String, image: ByteArray): Result<String> {
        val reqBody = RequestBody.create(prompt, image)
        val response = api.request(reqBody)

        if (response.isSuccess && response.getOrNull()?.error != null) {
            return Result.failure(Exception(response.getOrNull()?.error?.message))
        }

        return response.map(::extractFirstPromptResult)
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