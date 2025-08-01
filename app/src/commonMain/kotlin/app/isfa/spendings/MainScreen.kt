package app.isfa.spendings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import app.isfa.spendings.intent.ImageIntentData
import app.isfa.spendings.ui.IntentImageBottomSheet
import app.isfa.spendings.ui.SpendingsContent
import app.isfa.spendings.ui._component.BlackToast
import app.isfa.spendings.ui._component.BlackToastType
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.koin.koinScreenModel

class MainScreen(private val intentData: ImageIntentData?) : Screen {

    override val key: ScreenKey
        get() = intentData.toString()

    @Composable
    override fun Content() {
        val viewModel = koinScreenModel<MainViewModel>()
        val state by viewModel.state.collectAsState()

        LaunchedEffect(intentData) {
            viewModel.emitIntentData(intentData)
        }

        Box(modifier = Modifier.fillMaxSize()) {
            SpendingsContent(state.expanseList)
            
            if (state.intentProceed()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.5f))
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }

        if (state.errorMessage.isNotEmpty()) {
            BlackToast(
                modifier = Modifier.padding(bottom = 32.dp),
                message = state.errorMessage,
                type = BlackToastType.Short
            ) {
                viewModel.hideToast()
            }
        }

        if (state.isIntentProceed || state.intentDataProceed != null) {
            IntentImageBottomSheet(
                info = state.intentDataProceed ?: return,
                onSave = { model -> viewModel.storeExpenseData(model) },
                onDismiss = {  }
            )
        }
    }
}