package app.isfa.spendings

import app.isfa.spendings.di.appModule
import org.koin.core.context.startKoin

fun initKoin() {
    startKoin {
        modules(appModule)
    }
}