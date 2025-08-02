package app.isfa.spendings.test.viewmodel

import app.isfa.spendings.intent.ImageIntentData
import app.isfa.spendings.intent.ImageIntentDataPublisher
import app.isfa.spendings.intent.ImageIntentState
import app.isfa.spendings.robot.MainViewModelRobot
import app.isfa.spendings.util.DispatcherMainTest
import kotlin.test.Test
import kotlin.test.assertTrue

class MainViewModelTest : DispatcherMainTest() {

    @Test
    fun `intent image data loading state`() = test {
        val viewModel = MainViewModelRobot.create()
        ImageIntentDataPublisher.loading()

        assertTrue { viewModel.state.value.isIntentProceed }
    }

    @Test
    fun `intent image data fail state`() = test {
        val viewModel = MainViewModelRobot.create()
        ImageIntentDataPublisher.emitState(ImageIntentState.Fail)

        assertTrue { viewModel.state.value.errorMessage == "Uh-no! Please try again." }
    }

    @Test
    fun `intent image data receives image`() = test {
        val expectedValue = ImageIntentData(
            imageData = byteArrayOf(),
            fileName = "loren-ipsum.png",
            mimeType = "image/png"
        )

        val viewModel = MainViewModelRobot.create()
        ImageIntentDataPublisher.store(expectedValue)

        assertTrue { viewModel.state.value.currentIntentData == expectedValue }
    }

    @Test
    fun `extracts receipt from image intent data using gemini api`() = test {
        val extractedName = "Coffee"

        val imageIntentData = ImageIntentData(
            imageData = byteArrayOf(),
            fileName = "loren-ipsum.png",
            mimeType = "image/png"
        )

        ImageIntentDataPublisher.store(imageIntentData)

        val viewModel = MainViewModelRobot.create(
            expenseInfoUseCase = { extractable(extractedName) }
        )

        assertTrue { viewModel.state.value.intentDataProceed?.name == extractedName }
    }

    @Test
    fun `fails to extract receipt from image intent data using gemini api when unextractable`() = test {
        val imageIntentData = ImageIntentData(
            imageData = byteArrayOf(),
            fileName = "loren-ipsum.png",
            mimeType = "image/png"
        )

        ImageIntentDataPublisher.store(imageIntentData)

        val viewModel = MainViewModelRobot.create(
            expenseInfoUseCase = { unextractable() }
        )

        assertTrue { viewModel.state.value.intentDataProceed == null }
    }

    @Test
    fun `gemini api failure during receipt extraction`() = test {
        val imageIntentData = ImageIntentData(
            imageData = byteArrayOf(),
            fileName = "loren-ipsum.png",
            mimeType = "image/png"
        )

        ImageIntentDataPublisher.store(imageIntentData)

        val viewModel = MainViewModelRobot.create(
            expenseInfoUseCase = { failure() }
        )

        assertTrue { viewModel.state.value.intentDataProceed == null }
        assertTrue { viewModel.state.value.errorMessage.isNotEmpty() }
    }
}
