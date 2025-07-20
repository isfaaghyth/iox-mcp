package app.isfa.spendings.intent

data class ImageIntentData(
    val imageData: ByteArray,
    val fileName: String? = null,
    val mimeType: String? = null
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as ImageIntentData

        if (!imageData.contentEquals(other.imageData)) return false
        if (fileName != other.fileName) return false
        if (mimeType != other.mimeType) return false

        return true
    }

    override fun hashCode(): Int {
        var result = imageData.contentHashCode()
        result = 31 * result + (fileName?.hashCode() ?: 0)
        result = 31 * result + (mimeType?.hashCode() ?: 0)
        return result
    }
}