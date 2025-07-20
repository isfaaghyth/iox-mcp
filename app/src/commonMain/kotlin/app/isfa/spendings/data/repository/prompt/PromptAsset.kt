package app.isfa.spendings.data.repository.prompt

object PromptFile

fun interface PromptAsset {

    fun name(): String
}

val PromptFile.ReceiptExtractor: PromptAsset
    get() = PromptAsset { "prompt_receipt_extractor.txt" }