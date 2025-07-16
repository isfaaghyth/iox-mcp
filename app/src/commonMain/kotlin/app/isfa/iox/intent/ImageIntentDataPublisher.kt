package app.isfa.iox.intent

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

object ImageIntentDataPublisher {

    private var _capturedImages = MutableStateFlow<List<ImageIntentData>>(emptyList())
    val capturedImages get() = _capturedImages.asStateFlow()

    fun store(data: ImageIntentData) {
        _capturedImages.value += data
    }
}