package app.isfa.spendings.intent

import kotlinx.io.IOException
import java.awt.FileDialog
import java.awt.Frame
import java.io.File
import java.nio.file.Files
import java.util.Locale

actual class ImageIntentReceiver {

    actual fun onReceivedImage(imageData: ByteArray, fileName: String?) {
        val model = ImageIntentData(
            imageData = imageData,
            fileName = fileName
        )

        ImageIntentDataPublisher.store(model)
    }

    fun selectAndProcessImage(parentFrame: Frame? = null) {
        val dialog = FileDialog(parentFrame, "Select Image", FileDialog.LOAD)
        dialog.setFilenameFilter { _, name ->
            name.lowercase(Locale.getDefault()).endsWith(".jpg") ||
                    name.lowercase(Locale.getDefault()).endsWith(".jpeg") ||
                    name.lowercase(Locale.getDefault()).endsWith(".png") ||
                    name.lowercase(Locale.getDefault()).endsWith(".gif") ||
                    name.lowercase(Locale.getDefault()).endsWith(".bmp")
        }
        dialog.isVisible = true

        val directory = dialog.directory
        val file = dialog.file

        if (directory != null && file != null) {
            val selectedFile = File(directory, file)
            try {
                val imageData = Files.readAllBytes(selectedFile.toPath())
                val fileName = selectedFile.name
                onReceivedImage(imageData, fileName)
            } catch (e: IOException) {
                System.err.println("Error reading image file: ${e.message}")
                e.printStackTrace()
            }
        }
    }
}