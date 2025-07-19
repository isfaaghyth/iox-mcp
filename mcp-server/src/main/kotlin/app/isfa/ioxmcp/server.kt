package app.isfa.ioxmcp

import app.isfa.iox.data.repository.ExpenseRepository
import app.isfa.iox.di.initKoin
import io.modelcontextprotocol.kotlin.sdk.CallToolResult
import io.modelcontextprotocol.kotlin.sdk.Implementation
import io.modelcontextprotocol.kotlin.sdk.ServerCapabilities
import io.modelcontextprotocol.kotlin.sdk.TextContent
import io.modelcontextprotocol.kotlin.sdk.server.Server
import io.modelcontextprotocol.kotlin.sdk.server.ServerOptions

private val koin = initKoin().koin

fun configureServer(): Server {
    val repository = koin.get<ExpenseRepository>()

    val server = Server(
        Implementation(
            name = "mcp-kotlin expense server",
            version = "1.0.0"
        ),
        ServerOptions(
            capabilities = ServerCapabilities(
                prompts = ServerCapabilities.Prompts(listChanged = true),
                resources = ServerCapabilities.Resources(subscribe = true, listChanged = true),
                tools = ServerCapabilities.Tools(listChanged = true)
            )
        )
    )


    server.addTool(
        name = "get-expense-list",
        description = "The list of expense from daily transaction"
    ) {
        val expenses = repository.all().getOrNull() ?: listOf()
        CallToolResult(
            content = expenses.map {
                TextContent(it.toString())
            }
        )
    }

    return server
}