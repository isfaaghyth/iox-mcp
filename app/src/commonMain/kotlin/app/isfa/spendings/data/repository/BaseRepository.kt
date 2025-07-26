package app.isfa.spendings.data.repository

import app.isfa.spendings.data.NetworkClient
import app.isfa.spendings.data.url.NetworkUrls
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlin.coroutines.cancellation.CancellationException

open class BaseRepository {

    suspend inline fun <reified T> get(url: NetworkUrls): Result<T> {
        return safeCall {
            NetworkClient.client
                .get(url.string())
                .body()
        }
    }

    suspend inline fun <reified T, reified R> post(url: NetworkUrls, body: R): Result<T> {
        return safeCall {
            NetworkClient.client
                .post(url.string()) {
                    contentType(ContentType.Application.Json)
                    setBody(body)
                }
                .body()
        }
    }

    inline fun <reified T> safeCall(request: () -> T): Result<T> {
        return try {
            Result.success(request())
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}