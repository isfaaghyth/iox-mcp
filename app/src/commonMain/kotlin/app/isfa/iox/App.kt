package app.isfa.iox

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import app.isfa.iox.intent.ImageIntentDataPublisher
import cafe.adriel.voyager.navigator.Navigator
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    val capturedImage by ImageIntentDataPublisher.capturedImage.collectAsState()

    MaterialTheme {
        Navigator(MoneyMonitorScreen(capturedImage))
    }
}