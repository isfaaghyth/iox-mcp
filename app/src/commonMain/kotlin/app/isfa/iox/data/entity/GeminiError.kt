package app.isfa.iox.data.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GeminiError(
    @SerialName("code") val code: Int,
    @SerialName("message") val message: String,
)