package com.example.moneyapp.database

data class EntryState(
    val entrys: List<Entry> = emptyList(),
    val expense: String = "",
    val keyWord: String = "",
    val amount: String = "",
    val isAddingEntry: Boolean = false,
    val sortType: SortType = SortType.EXPENSE
)
