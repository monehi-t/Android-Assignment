package com.example.moneyapp.database

sealed interface EntryEvent {
    object SaveEntry: EntryEvent

    data class SetExpense(val expense: String): EntryEvent
    data class SetKeyWord(val keyWord: String): EntryEvent
    data class SetAmount(val amount: String): EntryEvent

    object ShowDialog: EntryEvent
    object HideDialog: EntryEvent

    data class sortEntrys(val sortType: SortType): EntryEvent
    data class deleteEntry(val entry: Entry): EntryEvent
}