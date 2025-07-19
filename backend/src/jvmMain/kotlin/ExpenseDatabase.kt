import app.isfa.iox.data.entity.ExpenseResponse

object ExpenseDatabase {

    private val items = mutableListOf<ExpenseResponse>()
    private var nextId = 1

    fun all() = items.toList()

    init {
        add(
            name = "Foo",
            amount = "123",
            category = "Food",
            time = "0"
        )

        add(
            name = "Bar",
            amount = "123",
            category = "Food",
            time = "0"
        )
    }

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
