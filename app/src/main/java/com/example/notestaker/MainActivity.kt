package com.example.notestaker


import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.notestaker.localDataBase.notedata.NoteDataBase
import com.example.notestaker.localDataBase.userdata.UserInfo
import com.example.notestaker.model.NoteViewModel
import com.example.notestaker.model.Resource
import com.example.notestaker.model.UserViewModel
import com.example.notestaker.navigation.AppNavHost
import com.example.notestaker.ui.theme.NotesTakerTheme
import com.example.notestaker.userRepository.NoteRepository
import com.example.notestaker.userRepository.UserRepository
import com.example.notestaker.user_case.note_case.NoteEvent


class MainActivity : ComponentActivity() {
    private val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            NoteDataBase::class.java,
            "Notes.db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }
    private val viewModelUser by viewModels<UserViewModel>(
        factoryProducer = {
            object : ViewModelProvider.Factory{
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return UserViewModel(UserRepository(db.userDao)) as T
                }
            }
        }
    )
    private val viewModel by viewModels<NoteViewModel>(
        factoryProducer = {
            object :ViewModelProvider.Factory{
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return NoteViewModel(NoteRepository(db.dao)) as T
                }
            }
        }
    )
    private var entryPoint="auth"
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NotesTakerTheme {
                // A surface container using the 'background' color from the theme
                val response by viewModelUser.data.observeAsState(initial = Resource.Loading)
                val userState by viewModelUser.state.collectAsState()
                val owner by viewModel.owner.collectAsState()
                viewModelUser.fetchData()
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    when (val resource = response) {
                        is Resource.Loading -> {
                            // Show loading indicator
                            Box(contentAlignment = Alignment.Center) {
                                CircularProgressIndicator()
                            }
                        }
                        is Resource.Success -> {
                            // Use resource.data to update UI with successful response
                            if(resource.data.isNotEmpty()){
                                val user= resource.data[0]
                                viewModel.setLoginUser(user)
                                viewModel.onEvent(NoteEvent.GetNotes)
                                Log.d("Main Activity called", "onCreate: $owner")
                                entryPoint="note"

                            }
                            AppNavHost(
                                    noteViewModel=viewModel,
                                    noteOnEvent = viewModel::onEvent,
                                    entryPoint = entryPoint,
                                    state = userState,
                                    userOnEvent = viewModelUser::onEvent,
                                owner = owner
                            )
                        }
                        is Resource.Error -> {
                            // Handle error, show an error message, etc.

                        }

                    }

                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        viewModelUser.getLoginUser()
    }

}

