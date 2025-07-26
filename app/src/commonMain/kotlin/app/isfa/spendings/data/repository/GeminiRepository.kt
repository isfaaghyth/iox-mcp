package app.isfa.spendings.data.repository

import app.isfa.spendings.data.NetworkClient
import app.isfa.spendings.data.entity.GeminiResponse
import app.isfa.spendings.data.repository.request.GeminiRequestBody
import app.isfa.spendings.data.url.NetworkUrl
import app.isfa.spendings.data.url.prompt
import app.isfa.spendings.util.safeCall
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

interface GeminiRepository {

    suspend fun request(prompt: String, image: ByteArray?): Result<String>
}

internal class GeminiRepositoryImpl : GeminiRepository {

    override suspend fun request(prompt: String, image: ByteArray?): Result<String> {
        if (prompt.isEmpty()) return Result.failure(Exception("Prompt fails to parse."))

        val body = GeminiRequestBody.create(prompt, image)
        return safeCall {
            NetworkClient.client
                .post(NetworkUrl.Gemini.prompt.string()) {
                    contentType(ContentType.Application.Json)
                    setBody(body)
                }
                .body<GeminiResponse>()
                .extractResult()
        }
    }

    private fun GeminiResponse.extractResult() =
        candidates
            .firstOrNull()
            ?.content
            ?.parts
            ?.firstOrNull()
            ?.text
            .orEmpty()
}