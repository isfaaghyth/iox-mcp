package app.isfa.iox

import androidx.compose.runtime.remember
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import app.isfa.iox.di.appModule
import app.isfa.iox.intent.ImageIntentReceiver
import org.koin.core.context.startKoin

fun main() {
    startKoin { modules(appModule) }

    application {
        val imageIntentReceiver = remember { ImageIntentReceiver() }
        imageIntentReceiver.selectAndProcessImage()

        Window(
            onCloseRequest = ::exitApplication,
            title = "Ioxmcp",
            alwaysOnTop = true
        ) {
            App()
        }
    }
}