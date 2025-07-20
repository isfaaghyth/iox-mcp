package app.isfa.spendings.intent

import android.content.Context
import android.content.Intent
import android.net.Uri
import java.io.ByteArrayOutputStream
import java.io.InputStream

actual class ImageIntentReceiver(private val context: Context) {

    actual fun onReceivedImage(imageData: ByteArray, fileName: String?) {
        val model = ImageIntentData(
            imageData = imageData,
            fileName = fileName
        )

        ImageIntentDataPublisher.store(model)
    }

    fun processSharedIntent(intent: Intent) {
        when (intent.action) {
            Intent.ACTION_SEND -> {
                if (intent.type?.startsWith("image/") == true) {
                    handleSingleImage(intent)
                }
            }
        }
    }

    private fun handleSingleImage(intent: Intent) {
        val imageUri = intent.getParcelableExtra<Uri>(Intent.EXTRA_STREAM)

        imageUri?.let { uri ->
            val imageData = readImageFromUri(uri)
            val fileName = getFileNameFromUri(uri)
            imageData?.let { data ->
                onReceivedImage(data, fileName)
            }
        }
    }

    private fun handleMultipleImages(intent: Intent) {
        val imageUris = intent.getParcelableArrayListExtra<Uri>(Intent.EXTRA_STREAM)
        imageUris?.forEach { uri ->
            val imageData = readImageFromUri(uri)
            val fileName = getFileNameFromUri(uri)
            imageData?.let { data ->
                onReceivedImage(data, fileName)
            }
        }
    }

    private fun readImageFromUri(uri: Uri): ByteArray? {
        return try {
            val inputStream: InputStream? = context.contentResolver.openInputStream(uri)

            inputStream?.use { stream ->
                val buffer = ByteArrayOutputStream()
                val bufferSize = 1024
                val data = ByteArray(bufferSize)
                var bytesRead: Int
                while (stream.read(data, 0, bufferSize).also { bytesRead = it } != -1) {
                    buffer.write(data, 0, bytesRead)
                }
                buffer.toByteArray()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private fun getFileNameFromUri(uri: Uri): String? {
        return try {
            val cursor = context.contentResolver.query(uri, null, null, null, null)
            cursor?.use {
                if (it.moveToFirst()) {
                    val displayNameIndex = it.getColumnIndex(android.provider.OpenableColumns.DISPLAY_NAME)
                    if (displayNameIndex != -1) {
                        it.getString(displayNameIndex)
                    } else null
                } else null
            }
        } catch (e: Exception) {
            null
        }
    }
}