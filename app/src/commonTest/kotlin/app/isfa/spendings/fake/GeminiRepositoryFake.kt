package app.isfa.spendings.fake

import app.isfa.spendings.data.repository.GeminiRepository

class GeminiRepositoryFake : GeminiRepository {

    var result: Result<String>? = null

    override suspend fun request(prompt: String, image: ByteArray?): Result<String> {
        return result ?: Result.failure(Exception("Result is undefined."))
    }
}