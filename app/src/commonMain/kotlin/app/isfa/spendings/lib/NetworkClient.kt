package app.isfa.spendings.lib

import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

object NetworkClient {

    val ExpenseBaseUrl get() = "http://10.0.2.2:8080/"
    val McpExpenseBaseUrl get() = "http://0.0.0.0:8080/"
    val GeminiBaseUrl get() = "https://generativelanguage.googleapis.com/"

    val create = HttpClient {
        install(ContentNegotiation) {
            json(
                Json {
                    isLenient = true
                    ignoreUnknownKeys = true
                }
            )
        }
    }
}