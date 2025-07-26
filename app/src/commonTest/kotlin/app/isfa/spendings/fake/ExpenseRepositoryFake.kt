package app.isfa.spendings.fake

import app.isfa.spendings.data.entity.ExpenseResponse
import app.isfa.spendings.data.repository.ExpenseRepository
import app.isfa.spendings.data.repository.request.ExpenseRequestBody

class ExpenseRepositoryFake : ExpenseRepository {

    var result: Result<List<ExpenseResponse>>? = null

    override suspend fun all() = result ?: Result.failure(Exception("fail to retrieve all data"))

    override suspend fun create(body: ExpenseRequestBody) =
        result ?: Result.failure(Exception("fail to store the data"))
}