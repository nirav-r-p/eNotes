package com.example.notestaker.navigation
//

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.notestaker.screens.EditNote
import com.example.notestaker.screens.NotesScreen
import com.example.notestaker.user_case.NoteEvent
import com.example.notestaker.user_case.NoteState


@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun AppNavHost(
    state: NoteState,
    onEvent:(NoteEvent)->Unit
){
    val navController= rememberNavController()
    NavHost(navController = navController, startDestination = "NoteScreen",
        builder ={
        composable("NoteScreen"){
            NotesScreen(state = state, onEvent = onEvent,navController)
        }
        composable(
            "EditNote",
        ){
            EditNote(state = state, onEvent = onEvent,navController = navController)
        }
       }
    )
}