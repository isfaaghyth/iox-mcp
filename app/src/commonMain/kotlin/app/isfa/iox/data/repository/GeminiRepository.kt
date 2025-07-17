package app.isfa.iox.data.repository

import app.isfa.iox.data.api.GeminiApi
import app.isfa.iox.data.entity.GeminiResponse
import app.isfa.iox.data.repository.request.RequestBody

interface GeminiRepository {

    suspend fun request(prompt: String, image: ByteArray): Result<GeminiResponse>
}

internal class GeminiRepositoryImpl(private val api: GeminiApi) : GeminiRepository {

    override suspend fun request(prompt: String, image: ByteArray): Result<GeminiResponse> {
        val reqBody = RequestBody.create(prompt, image)
        return api.request(reqBody)
    }
}