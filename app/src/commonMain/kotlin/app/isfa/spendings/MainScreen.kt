package app.isfa.spendings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import app.isfa.spendings.intent.ImageIntentData
import app.isfa.spendings.ui.IntentImageBottomSheet
import app.isfa.spendings.ui.SpendingsContent
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel

class MainScreen(private val intentData: ImageIntentData?) : Screen {

    @Composable
    override fun Content() {
        val viewModel = koinScreenModel<MainViewModel>()
        val state by viewModel.state.collectAsState()

        // Extract receipt information with Gemini if there's intent data received
        LaunchedEffect(intentData) {
            viewModel.setIntentImageData(intentData)
        }

        Box(modifier = Modifier.fillMaxSize()) {
            // History expense list
            SpendingsContent(state.expanseList)
            
            // Black overlay when bottom sheet is shown
            if (state.isIntentProceed) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.5f))
                )
            }
        }

        // Show bottom sheet with loading state immediately, then with data when ready
        if (state.isIntentProceed || state.intentDataProceed != null) {
            IntentImageBottomSheet(
                info = state.intentDataProceed ?: return,
                onSave = { model -> viewModel.saveData(model) },
                onDismiss = {  }
            )
        }
    }
}