package app.isfa.iox.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.isfa.iox.intent.ImageIntentData
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun MoneyMonitorContent(images: List<ImageIntentData>) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Image Receiver",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (images.isEmpty()) {
            Text("No images received yet. Share an image to this app!")
        } else {
            Text("Received ${images.size} image(s)")
            // Here you can add image display logic
        }
    }
}

@Preview
@Composable
fun MoneyMonitorContentPreview() {
    MoneyMonitorContent(listOf())
}