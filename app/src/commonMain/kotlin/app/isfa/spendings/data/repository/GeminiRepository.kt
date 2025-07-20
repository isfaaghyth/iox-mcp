package app.isfa.spendings.data.repository

import app.isfa.spendings.data.NetworkClient
import app.isfa.spendings.data.Urls
import app.isfa.spendings.data.entity.GeminiResponse
import app.isfa.spendings.data.repository.request.GeminiRequestBody
import app.isfa.spendings.data.saveCall
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

interface GeminiRepository {

    suspend fun request(prompt: String, image: ByteArray): Result<String>
}

internal class GeminiRepositoryImpl : GeminiRepository {

    private val gemini20Flash = "/v1beta/models/gemini-2.0-flash:generateContent?key=XXX"

    override suspend fun request(prompt: String, image: ByteArray): Result<String> {
        val body = GeminiRequestBody.create(prompt, image)
        return saveCall {
            NetworkClient
                .client
                .post("${Urls.Gemini}$gemini20Flash") {
                    contentType(ContentType.Application.Json)
                    setBody(body)
                }
                .body<GeminiResponse>()
                .getPromptResult()
        }
    }

    private fun GeminiResponse.getPromptResult() =
        candidates
            .firstOrNull()
            ?.content
            ?.parts
            ?.firstOrNull()
            ?.text
            .orEmpty()
}