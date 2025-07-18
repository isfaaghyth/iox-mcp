package app.isfa.iox.ui._organism

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ioxmcp.app.generated.resources.Res
import ioxmcp.app.generated.resources.ic_wallet
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun EmptyStateContent() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(Res.drawable.ic_wallet),
                modifier = Modifier.size(100.dp),
                contentDescription = null
            )

            Text(
                text = "Yay! there's no expense here.",
                fontSize = 16.sp,
                color = Color(0xFF9CA3AF),
            )
        }
    }
}

@Preview
@Composable
fun EmptyStateContentPreview() {
    EmptyStateContent()
}