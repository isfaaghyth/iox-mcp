package app.isfa.iox

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import app.isfa.iox.di.appModule
import org.koin.core.context.startKoin

fun main() = application {
    startKoin { modules(appModule) }

    Window(
        onCloseRequest = ::exitApplication,
        title = "Ioxmcp",
        alwaysOnTop = true
    ) {
        App()
    }
}