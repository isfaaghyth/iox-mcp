package app.isfa.spendings.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import app.isfa.spendings.MainUiState
import app.isfa.spendings.ui._component.ExpenseGroup
import app.isfa.spendings.ui._organism.EmptyStateContent
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun SpendingsContent(state: MainUiState) {
    val expenses = state.expanseList

    Box(Modifier.background(Color.White).fillMaxSize()) {
        when {
            expenses == null -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            expenses.isEmpty() && !state.intentProceed() -> EmptyStateContent()
            else -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(expenses) { expenses ->
                        ExpenseGroup(expenses)
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun SpendingsContentPreview() {
    SpendingsContent(MainUiState.Default)
}