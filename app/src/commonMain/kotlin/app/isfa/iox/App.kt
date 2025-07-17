package app.isfa.iox

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import app.isfa.iox.intent.ImageIntentDataPublisher
import app.isfa.iox.ui.MoneyMonitorContent
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    val capturedImages by ImageIntentDataPublisher.capturedImages.collectAsState()

    MaterialTheme {
        MoneyMonitorContent(capturedImages)
    }
}