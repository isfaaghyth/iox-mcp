import app.isfa.spendings.data.entity.ExpenseResponse

object LruDatabaseManager {

    private val items = mutableListOf<ExpenseResponse>()
    private var nextId = 1

    init {
        ExpenseFaker.create().map(::add)
    }

    fun all() = items.toList()

    fun add(body: CreateExpenseRequestBody): ExpenseResponse {
        val item = ExpenseResponse(
            id = nextId++,
            name = body.name,
            amount = body.amount,
            category = body.category,
            time = body.time,
        )

        items.add(item)

        return item
    }
}
