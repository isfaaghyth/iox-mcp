package app.isfa.iox.di

import app.isfa.iox.AppViewModel
import app.isfa.iox.data.api.EndpointApi
import app.isfa.iox.data.repository.ExpenseRepository
import app.isfa.iox.data.repository.ExpenseRepositoryImpl
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
    single<EndpointApi> { EndpointApi.create() }
    singleOf(::PromptRepositoryImpl) bind PromptRepository::class
    factoryOf(::GeminiRepositoryImpl) bind GeminiRepository::class
    factoryOf(::ExpenseRepositoryImpl) bind ExpenseRepository::class

    factoryOf(::GetExpenseInfoUseCase)
    factoryOf(::GetExpenseListUseCase)

    factoryOf(::AppViewModel)
}