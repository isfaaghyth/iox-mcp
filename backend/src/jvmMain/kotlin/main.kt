import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.routing
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
class CreateExpenseRequestBody(
    val name: String,
    val amount: String,
    val category: String,
    val time: String
)

fun main() {
    embeddedServer(
        factory = Netty,
        port = 8080,
        host = "0.0.0.0",
        module = Application::module
    ).start(wait = true)
}

fun Application.module() {
    install(ContentNegotiation) {
        json(Json {
            prettyPrint = true
            isLenient = true
        })
    }

    routing {
        get("/expenses") {
            call.respond(ExpenseDb.all())
        }

        post("/expense/create") {
            try {
                val request = call.receive<CreateExpenseRequestBody>()
                val item = ExpenseDb.add(
                    name = request.name,
                    amount = request.amount,
                    category = request.category,
                    time = request.time
                )

                call.respond(HttpStatusCode.Created, item)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest, "Invalid request body")
            }
        }
    }
}