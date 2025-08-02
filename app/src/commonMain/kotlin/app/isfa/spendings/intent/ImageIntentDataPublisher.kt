package app.isfa.spendings.intent

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

object ImageIntentDataPublisher {

    private var _capturedImage = MutableStateFlow<ImageIntentState>(ImageIntentState.None)
    val capturedImage get() = _capturedImage.asStateFlow()

    fun loading() = emitState(ImageIntentState.Loading)
    fun store(data: ImageIntentData) = emitState(ImageIntentState.Succeed(data))
    fun reset() = emitState(ImageIntentState.None)

    fun emitState(state: ImageIntentState) {
        _capturedImage.value = state
    }
}