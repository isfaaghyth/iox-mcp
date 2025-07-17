package app.isfa.iox.data.repository.request

import io.ktor.util.encodeBase64
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
data class RequestBody(
    @SerialName("contents") val contents: List<RequestContentItem>
) {

    companion object {
        fun create(prompt: String, image: ByteArray): String {
            val parts = mutableListOf<RequestContentPart>()
            parts.add(RequestContentPart(text = prompt))
            parts.add(
                RequestContentPart(
                    data = RequestInlineData(
                        mimeType = "image/jpeg",
                        data = image.encodeBase64()
                    )
                )
            )

            val body = RequestBody(contents = listOf(RequestContentItem(parts)))
            return Json.encodeToString(body)
        }
    }
}

@Serializable
data class RequestContentItem(
    @SerialName("parts") val parts: List<RequestContentPart>
)

@Serializable
data class RequestContentPart(
    @SerialName("text") val text: String = "",
    @SerialName("inlineData") val data: RequestInlineData? = null
)

@Serializable
data class RequestInlineData(
    @SerialName("mimeType") val mimeType: String,
    @SerialName("data") val data: String
)