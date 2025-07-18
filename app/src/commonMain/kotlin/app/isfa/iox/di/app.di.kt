package app.isfa.iox.di

import app.isfa.iox.AppViewModel
import app.isfa.iox.data.api.GeminiApi
import app.isfa.iox.data.repository.GeminiRepository
import app.isfa.iox.data.repository.GeminiRepositoryImpl
import app.isfa.iox.data.repository.PromptRepository
import app.isfa.iox.data.repository.PromptRepositoryImpl
import app.isfa.iox.domain.GetExpenseInfoUseCase
import app.isfa.iox.domain.GetExpenseListUseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {
    single<GeminiApi> { GeminiApi.create() }
    singleOf(::PromptRepositoryImpl) bind PromptRepository::class
    factoryOf(::GeminiRepositoryImpl) bind GeminiRepository::class

    factoryOf(::GetExpenseInfoUseCase)
    factoryOf(::GetExpenseListUseCase)

    factoryOf(::AppViewModel)
}