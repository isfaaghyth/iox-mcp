package app.isfa.iox.data.api

import app.isfa.iox.data.entity.ExpenseResponse
import app.isfa.iox.data.entity.GeminiResponse
import app.isfa.iox.data.repository.request.ExpenseRequestBody
import app.isfa.iox.lib.NetworkClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.serialization.json.Json

interface EndpointApi {

    suspend fun extractInfo(request: String): Result<GeminiResponse>

    suspend fun expenses(fromMcp: Boolean): Result<List<ExpenseResponse>>
    suspend fun createExpense(body: ExpenseRequestBody): Result<List<ExpenseResponse>>

    companion object Companion {
        private const val MODEL_VERSION = "gemini-2.0-flash"
        private const val URL = "v1beta/models/$MODEL_VERSION:generateContent?key=XXX"

        fun create() = object : EndpointApi {
            override suspend fun extractInfo(request: String): Result<GeminiResponse> {
                return runCatching {
                    NetworkClient
                        .create
                        .post("${NetworkClient.GeminiBaseUrl}$URL") { setBody(request) }
                        .body()
                }
            }

            override suspend fun expenses(fromMcp: Boolean): Result<List<ExpenseResponse>> {
                val baseUrl = if (fromMcp) NetworkClient.McpExpenseBaseUrl else NetworkClient.ExpenseBaseUrl
                return runCatching {
                    NetworkClient
                        .create
                        .get("${baseUrl}expenses") {
                            contentType(ContentType.Application.Json)
                        }
                        .body()
                }
            }

            override suspend fun createExpense(body: ExpenseRequestBody): Result<List<ExpenseResponse>> {
                val asJson = Json.encodeToString(body)

                return runCatching {
                    NetworkClient
                        .create
                        .post("${NetworkClient.ExpenseBaseUrl}expense/create") {
                            contentType(ContentType.Application.Json)
                            setBody(asJson)
                        }
                        .body()
                }
            }
        }
    }
}