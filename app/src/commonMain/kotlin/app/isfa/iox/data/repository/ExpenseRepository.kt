package app.isfa.iox.data.repository

import app.isfa.iox.data.api.EndpointApi
import app.isfa.iox.data.entity.ExpenseResponse
import app.isfa.iox.data.repository.request.ExpenseRequestBody

interface ExpenseRepository {

    suspend fun all(): Result<List<ExpenseResponse>>
    suspend fun create(body: ExpenseRequestBody)
}

class ExpenseRepositoryImpl(private val api: EndpointApi) : ExpenseRepository {

    override suspend fun all(): Result<List<ExpenseResponse>> {
        return api.expenses()
    }

    override suspend fun create(body: ExpenseRequestBody) {
        api.createExpense(body)
    }

}