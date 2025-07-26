package app.isfa.spendings.data.url

data class UrlModel(
    val baseUrl: String = "",
    val port: Int = 0,
    val path: String = "",
    val params: Map<String, String> = emptyMap()
)
