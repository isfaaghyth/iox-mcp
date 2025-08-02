package app.isfa.spendings.intent

sealed class ImageIntentState {
    data class Succeed(val data: ImageIntentData?): ImageIntentState()
    data object Loading : ImageIntentState()
    data object Fail : ImageIntentState()
    data object None : ImageIntentState()
}