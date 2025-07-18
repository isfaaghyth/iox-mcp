package app.isfa.iox.di

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import app.isfa.iox.domain.GetGeminiUseCase
import app.isfa.iox.ui.intent.MoneyMonitorViewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

object MoneyMonitorProvider : KoinComponent {

    private val useCase: GetGeminiUseCase by inject()

    @Composable
    fun viewModel() = viewModel {
        MoneyMonitorViewModel(
            useCase = useCase
        )
    }
}