package app.isfa.iox

import app.isfa.iox.di.appModule
import org.koin.core.context.startKoin

fun initKoin() {
    startKoin {
        modules(appModule)
    }
}