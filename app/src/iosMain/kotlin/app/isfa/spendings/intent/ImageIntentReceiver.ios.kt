package app.isfa.spendings.intent

import app.isfa.spendings.ext.toByteArray
import platform.Foundation.NSData
import platform.Foundation.NSURL
import platform.Foundation.dataWithContentsOfURL

actual class ImageIntentReceiver {

    actual fun onReceivedImage(imageData: ByteArray, fileName: String?) {
        val model = ImageIntentData(
            imageData = imageData,
            fileName = fileName
        )

        ImageIntentDataPublisher.store(model)
    }

    fun handleSharedImages(urls: List<NSURL>) {
        urls.forEach { url ->
            processImageUrl(url)
        }
    }

    private fun processImageUrl(url: NSURL) {
        val data = NSData.dataWithContentsOfURL(url)

        data?.let { nsData ->
            val imageData = nsData.toByteArray()
            val fileName = url.lastPathComponent

            onReceivedImage(imageData, fileName)
        }
    }
}