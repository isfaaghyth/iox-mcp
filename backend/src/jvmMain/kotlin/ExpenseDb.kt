import app.isfa.iox.data.entity.ExpenseResponse

object ExpenseDb {

    private val items = mutableListOf<ExpenseResponse>()
    private var nextId = 1

    fun all() = items.toList()

    fun add(
        name: String,
        amount: String,
        category: String,
        time: String
    ): ExpenseResponse {
        val item = ExpenseResponse(
            id = nextId++,
            name = name,
            amount = amount,
            category = category,
            time = time,
        )
        items.add(item)
        return item
    }
}
