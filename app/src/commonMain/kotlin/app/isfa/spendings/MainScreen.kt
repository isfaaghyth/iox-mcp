@file:OptIn(ExperimentalMaterial3Api::class)

package app.isfa.spendings

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.isfa.spendings.ui.IntentImageBottomSheet
import app.isfa.spendings.ui.SpendingsContent
import app.isfa.spendings.ui._component.AnimatedLoadingBar
import app.isfa.spendings.ui._component.BlackToast
import app.isfa.spendings.ui._component.BlackToastType
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import org.jetbrains.compose.resources.stringResource
import spendings.app.generated.resources.Res
import spendings.app.generated.resources.app_title

class MainScreen : Screen {

    @Composable
    override fun Content() {
        val viewModel = koinScreenModel<MainViewModel>()
        val state by viewModel.state.collectAsState()

        Scaffold(
            topBar = {
                Column(Modifier.fillMaxWidth()) {
                    TopAppBar(
                        title = {
                            Text(
                                stringResource(Res.string.app_title),
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp
                            )
                        },
                        colors = TopAppBarDefaults.topAppBarColors(Color.White)
                    )

                    AnimatedLoadingBar(isLoading = state.intentProceed())
                }
            }
        ) { contentPadding ->
            Box(Modifier.padding(contentPadding).fillMaxSize()) {
                SpendingsContent(state)
            }

            if (state.errorMessage.isNotEmpty()) {
                BlackToast(
                    modifier = Modifier.padding(bottom = 32.dp),
                    message = state.errorMessage,
                    type = BlackToastType.Short,
                    onToastDismissed = { viewModel.sendAction(HideToast) }
                )
            }
        }

        if (state.isIntentProceed || state.intentDataProceed != null) {
            IntentImageBottomSheet(
                info = state.intentDataProceed ?: return,
                onSave = { model -> viewModel.sendAction(SaveExpense(model)) },
                onDismiss = { viewModel.sendAction(DismissExpense) }
            )
        }
    }
}