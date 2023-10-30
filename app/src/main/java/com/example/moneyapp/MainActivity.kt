package com.example.moneyapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.moneyapp.database.EntryDatabase
import com.example.moneyapp.database.EntryViewModel
import com.example.moneyapp.ui.theme.MoneyAppTheme

class MainActivity : ComponentActivity() {

    //Initialize Room DataBase
    private val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            EntryDatabase::class.java,
            "entry.db"
        ).build()
    }
    private val viewModel by viewModels<EntryViewModel>(
        factoryProducer = {
            @Suppress("UNCHECKED_CAST")
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return EntryViewModel(db.dao) as T
                }
            }
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MoneyAppTheme {
                val state by viewModel.state.collectAsState()
                EntryScreen(state = state, onEvent = viewModel::onEvent )
            }
        }
    }
}