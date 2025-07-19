package app.isfa.iox.di

import org.koin.core.context.startKoin

fun initKoin() = startKoin {
    modules(appModule)
}