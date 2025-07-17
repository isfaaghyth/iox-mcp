package app.isfa.iox.di

import app.isfa.iox.domain.GetGeminiUseCase
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

object MoneyMonitorProvider : KoinComponent {

    private val useCase: GetGeminiUseCase by inject()

//    fun viewModel() = view
}