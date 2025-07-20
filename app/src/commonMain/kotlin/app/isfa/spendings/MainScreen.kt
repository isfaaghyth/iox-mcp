package app.isfa.spendings

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import app.isfa.spendings.intent.ImageIntentData
import app.isfa.spendings.ui.IntentImageBottomSheet
import app.isfa.spendings.ui.SpendingsContent
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel

class MainScreen(private val intentData: ImageIntentData?) : Screen {

    @Composable
    override fun Content() {
        val viewModel = koinScreenModel<AppViewModel>()

        val expenseInfo by viewModel.intentState.collectAsState()
        val expenseList by viewModel.expenseListState.collectAsState()

        var shouldExpenseInfoSheetShown by remember { mutableStateOf(false) }

        // Extract receipt information with Gemini if there's intent data received
        LaunchedEffect(intentData) {
            viewModel.emit(intentData)
        }

        // Showing the bottom sheet if the info is parsable
        expenseInfo?.let {
            shouldExpenseInfoSheetShown = true

            IntentImageBottomSheet(
                info = it,
                onSave = { model -> viewModel.store(model) },
                onDismiss = { shouldExpenseInfoSheetShown = false }
            )
        }

        // History expense list
        SpendingsContent(expenseList)
    }
}