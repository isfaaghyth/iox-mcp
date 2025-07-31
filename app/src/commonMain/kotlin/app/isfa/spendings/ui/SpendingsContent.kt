package app.isfa.spendings.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.isfa.spendings.domain.model.GroupExpenseUiModel
import app.isfa.spendings.ui._component.ExpenseGroup
import app.isfa.spendings.ui._organism.EmptyStateContent
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SpendingsContent(data: List<GroupExpenseUiModel>?) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Spendings",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White
                )
            )
        }
    ) { contentPadding ->
        Box(Modifier.padding(contentPadding).fillMaxSize()) {
            when {
                data == null -> {} // TODO: Loading state
                data.isEmpty() -> EmptyStateContent()
                else -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(data) { expenses ->
                            ExpenseGroup(expenses)
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun SpendingsContentPreview() {
    SpendingsContent(listOf())
}