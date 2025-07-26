package app.isfa.spendings.util

import kotlin.coroutines.cancellation.CancellationException

inline fun <reified T> safeCall(request: () -> T): Result<T> {
    return try {
        Result.success(request())
    } catch (e: CancellationException) {
        throw e
    } catch (e: Exception) {
        Result.failure(e)
    }
}