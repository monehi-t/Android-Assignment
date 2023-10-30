package com.example.moneyapp.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface EntryDao {

    //Insert new expense entry one doest exist or update expense with the same ID
    @Upsert
    suspend fun upsertEntry(entry: Entry)

    @Delete
    suspend fun deleteEntry(entry: Entry)

    //Filtering the order that the entries will be displayed
    @Query("SELECT * FROM entry ORDER BY expense ASC")
    fun getEntrysOrderedByExpense(): Flow<List<Entry>>

    @Query("SELECT * FROM entry ORDER BY keyWord ASC")
    fun getEntrysOrderedByKeyWord(): Flow<List<Entry>>

    @Query("SELECT * FROM entry ORDER BY amount ASC")
    fun getEntrysOrderedByAmount(): Flow<List<Entry>>
}