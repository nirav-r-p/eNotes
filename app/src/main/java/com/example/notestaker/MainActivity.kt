package com.example.notestaker


import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.notestaker.Navigation.AppNavHost

import com.example.notestaker.data.NoteDataBase
import com.example.notestaker.model.NoteViewModel
import com.example.notestaker.ui.theme.NotesTakerTheme


class MainActivity : ComponentActivity() {
    private val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            NoteDataBase::class.java,
            "Notes.db"
        ).build()
    }
    private val viewModel by viewModels<NoteViewModel>(
        factoryProducer = {
            object :ViewModelProvider.Factory{
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return NoteViewModel(db.dao) as T
                }
            }
        }
    )


    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NotesTakerTheme {
                // A surface container using the 'background' color from the theme
                val state by viewModel.state.collectAsState()
//                NotesScreen(state = state, onEvent = viewModel::onEvent)
                 AppNavHost(state = state,viewModel::onEvent)
            }
        }
    }
}

