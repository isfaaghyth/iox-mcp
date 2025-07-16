package app.isfa.iox.intent

// no-op
actual class ImageIntentReceiver {
    actual fun onReceivedImage(imageData: ByteArray, fileName: String?) = Unit
}