package com.example.moneyapp.database

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
class EntryViewModel(
    private val dao: EntryDao
): ViewModel() {

    private val _sortType = MutableStateFlow(SortType.EXPENSE)

    //Mapping the display order.
    private val _entry = _sortType
        .flatMapLatest { sortType ->
            when(sortType) {
                SortType.EXPENSE -> dao.getEntrysOrderedByExpense()
                SortType.KEYWORD -> dao.getEntrysOrderedByKeyWord()
                SortType.AMOUNT -> dao.getEntrysOrderedByAmount()
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())
    private val _state = MutableStateFlow(EntryState())

    //Combine the 3 flows into 1 flow
    val state = combine(_state, _sortType, _entry) {state, sortType, entrys ->
        state.copy(
            entrys = entrys,
            sortType = sortType
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), EntryState())

    //triggered when user interacts with app
    fun onEvent (event: EntryEvent) {
        when(event) {

            //Trigger to Delete Expense
            is EntryEvent.deleteEntry -> {
                viewModelScope.launch {
                    dao.deleteEntry(event.entry)
                }
            }

            //Trigger to Hide The Add New Dialog
            EntryEvent.HideDialog -> {
                _state.update { it. copy(
                    isAddingEntry = false
                ) }
            }

            // Trigger to save expense entry
            EntryEvent.SaveEntry -> {
                val expense = state.value.expense
                val keyWord = state.value.keyWord
                val amount = state.value.amount

                //Check if the input fields are empty
                if (expense.isBlank() || keyWord.isBlank() || amount.isBlank()){
                    return
                }

                val entry = Entry(
                    expense = expense,
                    keyWord = keyWord,
                    amount = amount
                )
                viewModelScope.launch {
                    dao.upsertEntry(entry)
                }
                _state.update { it.copy(
                    isAddingEntry = false,
                    expense = "",
                    keyWord = "",
                    amount = ""
                ) }
            }

            is EntryEvent.SetAmount -> {
                _state.update { it.copy(
                    amount = event.amount
                ) }
            }
            is EntryEvent.SetExpense -> {
                _state.update { it.copy(
                    expense = event.expense
                ) }
            }
            is EntryEvent.SetKeyWord -> {
                _state.update { it.copy(
                    keyWord = event.keyWord
                ) }
            }

            //Trigger to show "Add New Expense" Dialog
            EntryEvent.ShowDialog -> {
                _state.update { it.copy(
                    isAddingEntry = true
                ) }
            }

            is EntryEvent.sortEntrys -> {
                _sortType.value = event.sortType
            }
        }
    }
}