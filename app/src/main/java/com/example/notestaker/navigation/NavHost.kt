package com.example.notestaker.navigation
//

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.example.notestaker.model.NoteViewModel

import com.example.notestaker.screens.mainScreens.DailLogBox
import com.example.notestaker.screens.mainScreens.EditNote
import com.example.notestaker.screens.login_signIn_screens.LandingScreens

import com.example.notestaker.screens.mainScreens.NotesScreen
import com.example.notestaker.screens.login_signIn_screens.SigInScreen
import com.example.notestaker.user_case.note_case.NoteEvent
import com.example.notestaker.user_case.note_case.NoteState
import com.example.notestaker.user_case.userLogin.UserEvent
import com.example.notestaker.user_case.userLogin.UserState


@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun AppNavHost(
    state: UserState,
    noteState: NoteState,
    noteOnEvent:(NoteEvent)->Unit,
    userOnEvent:(UserEvent)->Unit,
    entryPoint:String,
){
    val navController= rememberNavController()
    NavHost(
        navController = navController, startDestination = entryPoint,
    ){
        navigation(
            startDestination = "LandingScreen",
            route="auth"
        ){
            composable("LandingScreen"){
                LandingScreens(navController)
            }
            composable("SigInPage"){
                SigInScreen(userEvent = userOnEvent, navController = navController, state = state, noteEvent = noteOnEvent)
            }
        }
        navigation(
            startDestination = "NotesScreen",
            route = "note"
        ){
            noteOnEvent(NoteEvent.GetNotes)
            composable("NotesScreen"){
                NotesScreen(state = noteState, userEvent = userOnEvent ,onEvent = noteOnEvent, navController = navController)
            }
            composable("EditNote"){
                EditNote(state = noteState, onEvent = noteOnEvent,navController = navController)
            }
            composable("DailLogBox"){
                DailLogBox(state = noteState,navController=navController)
            }
        }
    }
}