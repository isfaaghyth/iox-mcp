package app.isfa.spendings.intent

expect class ImageIntentReceiver {
    fun onReceivedImage(imageData: ByteArray, fileName: String?)
}