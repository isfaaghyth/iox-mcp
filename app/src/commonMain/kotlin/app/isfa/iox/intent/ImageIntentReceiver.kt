package app.isfa.iox.intent

expect class ImageIntentReceiver {
    fun onReceivedImage(imageData: ByteArray, fileName: String?)
}