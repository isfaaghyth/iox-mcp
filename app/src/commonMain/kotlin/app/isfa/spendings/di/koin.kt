package app.isfa.spendings.di

import org.koin.core.context.startKoin

fun initKoin() = startKoin {
    modules(appModule)
}