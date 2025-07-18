package app.isfa.iox.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ReceiptExtractorModel(
    @SerialName("name") val name: String,
    @SerialName("total_rupiah") val total: String,
    @SerialName("category") val category: String,
    @SerialName("time") val time: Long,
)