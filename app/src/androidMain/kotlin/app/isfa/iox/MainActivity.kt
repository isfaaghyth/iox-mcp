package app.isfa.iox

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.tooling.preview.Preview
import app.isfa.iox.intent.ImageIntentData
import app.isfa.iox.intent.ImageIntentReceiver

class MainActivity : ComponentActivity() {

    private var receivedImages = mutableStateListOf<ImageIntentData>()

    private val receiver by lazy {
        ImageIntentReceiver(
            context = this,
            listener = {
                receivedImages.add(it)
            }
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        handleIntent(intent)

        setContent {
            App(receivedImages)
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent) {
        receiver.processSharedIntent(intent)
    }
}
