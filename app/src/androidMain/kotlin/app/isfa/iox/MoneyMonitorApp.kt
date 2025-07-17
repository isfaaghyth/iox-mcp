package app.isfa.iox

import android.app.Application
import app.isfa.iox.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MoneyMonitorApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MoneyMonitorApp)
            modules(appModule)
        }
    }
}