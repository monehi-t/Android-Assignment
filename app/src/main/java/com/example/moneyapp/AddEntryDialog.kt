package com.example.moneyapp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.moneyapp.database.EntryEvent
import com.example.moneyapp.database.EntryState


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEntryDialog(
    state: EntryState,
    onEvent: (EntryEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        modifier = modifier,
        onDismissRequest = {
            onEvent(EntryEvent.HideDialog)
        },
        title = { Text(text = "Add Expense") },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                //Expense Text Field
                TextField(
                    value = state.expense,
                    onValueChange = {
                        onEvent(EntryEvent.SetExpense(it))
                    },
                    placeholder = {
                        Text(text = "Expense")
                    }
                )

                //Keyword Text Field
                TextField(
                    value = state.keyWord,
                    onValueChange = {
                        onEvent(EntryEvent.SetKeyWord(it))
                    },
                    placeholder = {
                        Text(text = "Keyword")
                    }
                )

                //Amount Text Field
                TextField(
                    value = state.amount,
                    onValueChange = {
                        onEvent(EntryEvent.SetAmount(it))
                    },
                    placeholder = {
                        Text(text = "amount")
                    }
                )
            }
        },
        // Custom button layout
        confirmButton = {
            Button(onClick = {
                onEvent(EntryEvent.SaveEntry)
            }) {
                Text(text = "Save")
            }
        }

    )
}