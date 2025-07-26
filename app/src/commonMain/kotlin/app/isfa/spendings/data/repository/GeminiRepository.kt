package app.isfa.spendings.data.repository

import app.isfa.spendings.data.entity.GeminiResponse
import app.isfa.spendings.data.repository.request.GeminiRequestBody
import app.isfa.spendings.data.url.NetworkUrl
import app.isfa.spendings.data.url.prompt

interface GeminiRepository {

    suspend fun request(prompt: String, image: ByteArray): Result<String>
}

internal class GeminiRepositoryImpl : BaseRepository(), GeminiRepository {

    override suspend fun request(prompt: String, image: ByteArray): Result<String> {
        val body = GeminiRequestBody.create(prompt, image)
        val post = post<GeminiResponse, String>(NetworkUrl.Gemini.prompt, body)
        return post.map(::extractResult)
    }

    private fun extractResult(res: GeminiResponse) =
        res.candidates
            .firstOrNull()
            ?.content
            ?.parts
            ?.firstOrNull()
            ?.text
            .orEmpty()
}