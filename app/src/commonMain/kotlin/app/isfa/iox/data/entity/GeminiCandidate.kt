package app.isfa.iox.data.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GeminiCandidate(
    @SerialName("content") val content: GeminiContent
)