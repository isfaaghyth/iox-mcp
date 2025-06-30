package app.isfa.iox

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Ioxmcp",
        alwaysOnTop = true
    ) {
        App()
    }
}