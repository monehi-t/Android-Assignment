package com.example.moneyapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.moneyapp.database.EntryEvent
import com.example.moneyapp.database.EntryState
import com.example.moneyapp.database.SortType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun  EntryScreen(
    state: EntryState,
    onEvent: (EntryEvent) -> Unit
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { onEvent(EntryEvent.ShowDialog) }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Expense Entry")

            }
        },
        modifier = Modifier.padding(16.dp)
    ) { padding ->
        if (state.isAddingEntry) {
            AddEntryDialog(state = state, onEvent = onEvent)
        }
        LazyColumn(
            contentPadding =  padding,
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp )
        ){
            // Logo Image
            item {
                Image(
                    painter = painterResource(id = R.drawable.expense_image), // Replace with your image resource
                    contentDescription = "Expenses Logo Image",
                    modifier = Modifier.fillMaxWidth()
                )
            }

            // Instruction Text Composable as the heading above the radio buttons
            item {
                Text(
                    text = "Select a Sort Type:",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(12.dp)
                )
            }

            // Filter Radio Buttons
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    verticalAlignment = Alignment.CenterVertically
                ){
                    SortType.values().forEach { sortType ->
                        Row(
                            modifier = Modifier.clickable {
                                onEvent(EntryEvent.sortEntrys(sortType))
                            },
                            verticalAlignment = CenterVertically
                        ){
                            RadioButton(selected = state.sortType == sortType,
                                onClick = { onEvent(EntryEvent.sortEntrys(sortType))
                            }
                            )
                            Text(text = sortType.name)
                        }
                    }
                }
            }
            items(state.entrys) { entry ->
                Row(
                    modifier = Modifier.fillMaxWidth()
                ){
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(text = "${entry.expense} ${entry.keyWord}",
                        fontSize = 20.sp
                        )
                        Text(text = entry.amount, fontSize = 14.sp)
                    }
                    IconButton(onClick = {
                        onEvent(EntryEvent.deleteEntry(entry))
                    }) {
                        Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete Expense Entry")

                    }
                }
            }
        }
    }

}