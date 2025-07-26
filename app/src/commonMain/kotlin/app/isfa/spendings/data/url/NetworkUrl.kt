package app.isfa.spendings.data.url

object NetworkUrl {
    object Gemini
    object Expense
}

fun interface NetworkUrls {
    fun string(): String
}