package app.isfa.spendings

import androidx.compose.runtime.remember
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import app.isfa.spendings.di.appModule
import app.isfa.spendings.intent.ImageIntentReceiver
import org.koin.core.context.startKoin

fun main() {
    startKoin { modules(appModule) }

    application {
        val imageIntentReceiver = remember { ImageIntentReceiver() }
        imageIntentReceiver.selectAndProcessImage()

        Window(
            onCloseRequest = ::exitApplication,
            title = "Spendings",
            alwaysOnTop = true
        ) {
            App()
        }
    }
}