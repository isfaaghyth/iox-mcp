package app.isfa.spendings

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import app.isfa.spendings.intent.ImageIntentDataPublisher
import cafe.adriel.voyager.navigator.Navigator

@Composable
fun App() {
    val capturedImage by ImageIntentDataPublisher.capturedImage.collectAsState()

    MaterialTheme {
        Navigator(MainScreen(capturedImage))
    }
}