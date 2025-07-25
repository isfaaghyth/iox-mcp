package app.isfa.ioxmcp

import io.modelcontextprotocol.kotlin.sdk.server.StdioServerTransport
import kotlinx.coroutines.Job
import kotlinx.coroutines.runBlocking
import kotlinx.io.asSink
import kotlinx.io.asSource
import kotlinx.io.buffered

fun main() {
    val server = configureServer()
    val transport = StdioServerTransport(
        System.`in`.asSource().buffered(),
        System.out.asSink().buffered()
    )

    runBlocking {
        val job = Job()
        server.onClose { job.complete() }
        server.connect(transport)
        job.join()
    }
}