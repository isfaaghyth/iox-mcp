package app.isfa.spendings.data.repository

import app.isfa.spendings.data.NetworkClient
import app.isfa.spendings.data.entity.ExpenseResponse
import app.isfa.spendings.data.repository.request.ExpenseRequestBody
import app.isfa.spendings.data.url.NetworkUrl
import app.isfa.spendings.data.url.create
import app.isfa.spendings.data.url.list
import app.isfa.spendings.util.safeCall
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

interface ExpenseRepository {

    suspend fun all(): Result<List<ExpenseResponse>>
    suspend fun create(body: ExpenseRequestBody): Result<List<ExpenseResponse>>
}

class ExpenseRepositoryImpl : ExpenseRepository {

    override suspend fun all(): Result<List<ExpenseResponse>> {
        return safeCall {
            NetworkClient.client
                .get(NetworkUrl.Expense.list.string())
                .body()
        }
    }

    override suspend fun create(body: ExpenseRequestBody): Result<List<ExpenseResponse>> {
        return safeCall {
            NetworkClient.client
                .post(NetworkUrl.Expense.create.string()) {
                    contentType(ContentType.Application.Json)
                    setBody(body)
                }
                .body()
        }
    }
}