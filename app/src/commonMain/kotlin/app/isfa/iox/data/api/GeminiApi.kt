package app.isfa.iox.data.api

import app.isfa.iox.data.entity.GeminiResponse
import app.isfa.iox.lib.NetworkClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody

interface GeminiApi {

    suspend fun request(request: String): Result<GeminiResponse>

    companion object {

        private const val GEMINI_PRO_VISION = "gemini-1.5-flash-latest"
        private const val URL = "v1beta/models/$GEMINI_PRO_VISION:generateContent?key=XXXX"

        fun create() = object : GeminiApi {
            override suspend fun request(request: String): Result<GeminiResponse> {
                return runCatching {
                    NetworkClient
                        .create
                        .post(URL) { setBody(request) }
                        .body()
                }
            }
        }
    }
}