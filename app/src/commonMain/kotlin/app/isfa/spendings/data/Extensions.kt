package app.isfa.spendings.data

import io.ktor.client.network.sockets.ConnectTimeoutException
import io.ktor.client.network.sockets.SocketTimeoutException
import io.ktor.client.plugins.HttpRequestTimeoutException
import kotlin.coroutines.cancellation.CancellationException

inline fun <reified T> saveCall(request: () -> T): Result<T> {
    return try {
        Result.success(request())
    } catch (e: HttpRequestTimeoutException) {
        Result.failure(e)
    } catch (e: ConnectTimeoutException) {
        Result.failure(e)
    } catch (e: SocketTimeoutException) {
        Result.failure(e)
    } catch (e: CancellationException) {
        throw e
    } catch (e: Exception) {
        Result.failure(e)
    }
}