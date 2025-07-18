package app.isfa.iox.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.isfa.iox.domain.model.ExpenseUiModel
import app.isfa.iox.ui._component.FormField
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IntentImageBottomSheet(
    info: ExpenseUiModel,
    onSave: (ExpenseUiModel) -> Unit,
    onDismiss: () -> Unit
) {
    val scope = rememberCoroutineScope()
    val state = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    var amount by remember {
        mutableStateOf(
            TextFieldValue(
                text = info.amount.toString(),
                selection = TextRange("0".length)
            )
        )
    }

    var name by remember { mutableStateOf(info.merchantName) }

    ModalBottomSheet(
        onDismissRequest = { onDismiss() },
        sheetState = state,
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Amount
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "IDR",
                    fontSize = 16.sp,
                    color = Color(0xFF6B7280),
                    fontWeight = FontWeight.Medium
                )

                BasicTextField(
                    value = amount,
                    onValueChange = { newText ->
                        if (newText.text.all { it.isDigit() }) {
                            amount = newText
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true,
                    textStyle = LocalTextStyle.current.copy(
                        fontSize = 48.sp,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF111827),
                    ),
                    cursorBrush = SolidColor(LocalContentColor.current)
                )
            }

            FormField(
                label = "Name",
                value = name,
                onValueChange = { name = it },
                isEditable = true
            )

            FormField(
                label = "When",
                value = info.asReadableTime(),
                isEditable = false
            )

            FormField(
                label = "Category",
                value = info.category,
                isEditable = false
            )

            Button(
                onClick = {
                    onSave(
                        info.copy(
                            amount = amount.text.toIntOrNull() ?: 0,
                            merchantName = name
                        )
                    )

                    scope.launch {
                        state.hide()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Save")
            }
        }
    }
}