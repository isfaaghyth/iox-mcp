package app.isfa.spendings.di

import app.isfa.spendings.MainViewModel
import app.isfa.spendings.data.repository.ExpenseRepository
import app.isfa.spendings.data.repository.ExpenseRepositoryImpl
import app.isfa.spendings.data.repository.GeminiRepository
import app.isfa.spendings.data.repository.GeminiRepositoryImpl
import app.isfa.spendings.data.repository.PromptRepository
import app.isfa.spendings.data.repository.PromptRepositoryImpl
import app.isfa.spendings.domain.GetExpenseInfoUseCase
import app.isfa.spendings.domain.GetExpenseListUseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {
    singleOf(::PromptRepositoryImpl) bind PromptRepository::class
    factoryOf(::GeminiRepositoryImpl) bind GeminiRepository::class
    factoryOf(::ExpenseRepositoryImpl) bind ExpenseRepository::class

    factoryOf(::GetExpenseInfoUseCase)
    factoryOf(::GetExpenseListUseCase)

    factoryOf(::MainViewModel)
}