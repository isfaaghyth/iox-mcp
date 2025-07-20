package app.isfa.spendings.data.repository

import app.isfa.spendings.data.NetworkClient
import app.isfa.spendings.data.Urls
import app.isfa.spendings.data.entity.ExpenseResponse
import app.isfa.spendings.data.repository.request.ExpenseRequestBody
import app.isfa.spendings.data.saveCall
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

interface ExpenseRepository {

    fun forMcp(value: Boolean)

    suspend fun all(): Result<List<ExpenseResponse>>
    suspend fun create(body: ExpenseRequestBody): Result<List<ExpenseResponse>>
}

class ExpenseRepositoryImpl : ExpenseRepository {

    /**
     * Since the MCP server runs on top of PC device, we need to determine
     * which host should we hit for fetching the data.
     */
    private var _forMcp = false

    private val host: Urls
        get() = if (_forMcp) Urls.LocalMcp else Urls.LocalAndroidEmulator

    override fun forMcp(value: Boolean) {
        _forMcp = value
    }

    override suspend fun all(): Result<List<ExpenseResponse>> {
        return saveCall {
            NetworkClient
                .client
                .get("${host.baseUrl}/expenses")
                .body<List<ExpenseResponse>>()
        }
    }

    override suspend fun create(body: ExpenseRequestBody): Result<List<ExpenseResponse>> {
        return saveCall {
            NetworkClient
                .client
                .post("${host.baseUrl}/expense/create") {
                    contentType(ContentType.Application.Json)
                    setBody(body)
                }
                .body()
        }
    }

}