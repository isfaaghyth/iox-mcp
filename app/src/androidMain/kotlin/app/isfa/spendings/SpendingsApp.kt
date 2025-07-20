package app.isfa.spendings

import android.app.Application
import app.isfa.spendings.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class SpendingsApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@SpendingsApp)
            modules(appModule)
        }
    }
}