@file:OptIn(ExperimentalMaterial3Api::class)

package app.isfa.spendings.ui._component

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun AnimatedLoadingBar(
    modifier: Modifier = Modifier,
    isLoading: Boolean = true,
    color: Color = Color.Black,
    backgroundColor: Color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f),
    height: Dp = 4.dp
) {
    if (!isLoading) return

    val infiniteTransition = rememberInfiniteTransition(label = "loading")

    val animatedProgress by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1500,
                easing = FastOutSlowInEasing
            ),
            repeatMode = RepeatMode.Restart
        ),
        label = "progress"
    )

    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .height(height)
            .clipToBounds()
    ) {
        val canvasWidth = size.width
        val canvasHeight = size.height

        drawRect(
            color = backgroundColor,
            size = size
        )

        val barWidth = canvasWidth * 0.3f
        val startX = (canvasWidth + barWidth) * animatedProgress - barWidth

        val gradient = Brush.horizontalGradient(
            colors = listOf(
                color.copy(alpha = 0f),
                color,
                color,
                color.copy(alpha = 0f)
            ),
            startX = startX,
            endX = startX + barWidth
        )

        drawRect(
            brush = gradient,
            topLeft = Offset(startX, 0f),
            size = androidx.compose.ui.geometry.Size(barWidth, canvasHeight)
        )
    }
}

@Composable
fun LoadingScreenExample() {
    var isLoading by remember { mutableStateOf(true) }

    Column {
        TopAppBar(
            title = { Text("My App") },
            actions = {
                TextButton(
                    onClick = { isLoading = !isLoading }
                ) {
                    Text(if (isLoading) "Stop" else "Start")
                }
            }
        )

        AnimatedLoadingBar(isLoading = isLoading)
    }
}

@Preview
@Composable
fun LoadingPreview() {
    MaterialTheme {
        LoadingScreenExample()
    }
}