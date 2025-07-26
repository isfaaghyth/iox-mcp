package app.isfa.spendings.data.url

import io.ktor.http.URLBuilder
import io.ktor.http.URLProtocol
import io.ktor.http.path
import io.ktor.util.appendAll

internal fun UrlModel.toUrlBuilder() = URLBuilder().apply {
    val model = this@toUrlBuilder // avoid mismatch type

    host = model.baseUrl
    port = model.port
    path(model.path)

    if (params.isNotEmpty()) {
        parameters.appendAll(model.params)
    }
}

internal fun UrlModel.create() =
    toUrlBuilder().buildString()

internal fun UrlModel.createWithHttps() =
    toUrlBuilder()
        .apply { protocol = URLProtocol.HTTPS }
        .buildString()
