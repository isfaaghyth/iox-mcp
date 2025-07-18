package app.isfa.iox.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ReceiptExtractorModel(
    @SerialName("merchant_name") val merchantName: String,
    @SerialName("total_rupiah") val total: String,
    @SerialName("category") val category: String,
) {

    companion object {
        val Empty get() = ReceiptExtractorModel(
            merchantName = "",
            total = "",
            category = ""
        )
    }
}