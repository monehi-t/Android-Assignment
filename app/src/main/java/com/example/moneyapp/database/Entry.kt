package com.example.moneyapp.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Entry(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val expense: String,
    val keyWord: String,
    val amount: String
)
