package app.isfa.iox.intent

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

object ImageIntentDataPublisher {

    private var _capturedImage = MutableStateFlow<ImageIntentData?>(null)
    val capturedImage get() = _capturedImage.asStateFlow()

    fun store(data: ImageIntentData) {
        _capturedImage.value = data
    }
}