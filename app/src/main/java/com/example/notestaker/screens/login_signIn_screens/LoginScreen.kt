package com.example.notestaker.screens.login_signIn_screens

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.notestaker.components.UserCard
import com.example.notestaker.user_case.note_case.NoteEvent
import com.example.notestaker.user_case.userLogin.UserEvent
import com.example.notestaker.user_case.userLogin.UserState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    state: UserState,
    userEvent: (UserEvent)->Unit,
    noteEvent: (NoteEvent)->Unit,
    navController: NavController
) {
    Scaffold {
        padding ->
        AlertDialog(
            dismissButton = {
                            TextButton(onClick = { navController.popBackStack()}) {
                                Text(text = "back")
                            }
            },
            onDismissRequest = { /*TODO*/ },
            confirmButton = {
                        TextButton(onClick = { /*TODO*/ }) {
                            Text(text = "Done")
                        }
            },
            modifier = Modifier.padding(padding),
            title = {
                Text(text = "Choose Account")
            },
            text = {
                LazyColumn(
                    content = {
                        items(state.userList){
                                user->
                            UserCard(user = user, delete = {userEvent(UserEvent.DeleteUser(user))}) {
                                userEvent(UserEvent.SetLogin(user.id))
                                noteEvent(NoteEvent.SetUser(user))
                                navController.navigate("note"){
                                    popUpTo("auth")
                                }
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            },
        )
    }
}


