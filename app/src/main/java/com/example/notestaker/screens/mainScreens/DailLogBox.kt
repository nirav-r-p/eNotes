package com.example.notestaker.screens.mainScreens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.navigation.NavController
import com.example.notestaker.localDataBase.userdata.UserInfo
import com.example.notestaker.user_case.note_case.NoteState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DailLogBox(
    state: NoteState,
    owner:UserInfo,
    navController: NavController
) {
    var password by remember {
        mutableStateOf("")
    }
    Scaffold {
       padding->
        Box(
            modifier = Modifier
                .fillMaxSize().padding(padding)
        ) {
            AlertDialog(
                onDismissRequest = { navController.popBackStack() },
                confirmButton = {
                      Button(onClick = {
                          if (password.trim() ==owner.notePassword) {
                              navController.popBackStack()
                              navController.navigate("EditNote")
                         }

                      }) {
                         Text(text = "Done")
                      }
                },
                text = {
                     OutlinedTextField(
                          value = password,
                          onValueChange = { password = it },
                          label = { Text(text = "Enter Password") },
                          visualTransformation = PasswordVisualTransformation()
                    )
                },
                title = {
                    Text(text = "This Notes Is Private")
                }
            )
        }
    }
}