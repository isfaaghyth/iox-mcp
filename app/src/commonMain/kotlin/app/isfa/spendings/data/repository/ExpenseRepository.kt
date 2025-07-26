package app.isfa.spendings.data.repository

import app.isfa.spendings.data.entity.ExpenseResponse
import app.isfa.spendings.data.repository.request.ExpenseRequestBody
import app.isfa.spendings.data.url.NetworkUrl
import app.isfa.spendings.data.url.prompt

interface ExpenseRepository {

    suspend fun all(): Result<List<ExpenseResponse>>
    suspend fun create(body: ExpenseRequestBody): Result<List<ExpenseResponse>>
}

class ExpenseRepositoryImpl : BaseRepository(), ExpenseRepository {

    override suspend fun all(): Result<List<ExpenseResponse>> {
        return get(NetworkUrl.Gemini.prompt)
    }

    override suspend fun create(body: ExpenseRequestBody): Result<List<ExpenseResponse>> {
        return post(NetworkUrl.Gemini.prompt, body)
    }
}