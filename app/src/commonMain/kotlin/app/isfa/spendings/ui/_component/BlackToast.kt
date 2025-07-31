package app.isfa.spendings.ui._component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds

enum class BlackToastType(val duration: Duration) {
    Short(3.seconds),
    Long(5.seconds)
}

private const val ANIMATION_DURATION_IN_MILLIS = 300

@Composable
fun BlackToast(
    modifier: Modifier = Modifier,
    message: String,
    type: BlackToastType = BlackToastType.Short,
    onToastDismissed: () -> Unit,
) {
    var toastShown by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        delay(ANIMATION_DURATION_IN_MILLIS.milliseconds) // gap for fade in animation
        toastShown = true
        delay(type.duration)
        toastShown = false
        delay(ANIMATION_DURATION_IN_MILLIS.milliseconds) // gap for fade out animation
        onToastDismissed()
    }
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(WindowInsets.safeDrawing.asPaddingValues()),
        contentAlignment = Alignment.BottomCenter
    ) {
        AnimatedVisibility(
            visible = toastShown,
            enter = fadeIn(animationSpec = tween(durationMillis = ANIMATION_DURATION_IN_MILLIS)),
            exit = fadeOut(animationSpec = tween(durationMillis = ANIMATION_DURATION_IN_MILLIS)),
        ) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp, vertical = 16.dp),
                shape = RoundedCornerShape(50),
                color = Color.Black,
                shadowElevation = 8.dp
            ) {
                Text(
                    modifier = Modifier.padding(16.dp),
                    text = message,
                    textAlign = TextAlign.Center,
                    color = Color.White,
                )
            }
        }
    }
}