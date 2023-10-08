package com.example.notestaker.screens.login_signIn_screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.notestaker.user_case.userLogin.UserEvent
import com.example.notestaker.user_case.userLogin.UserState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SigInScreen(
    state: UserState,
    onEvent:(UserEvent)->Unit,
    navController: NavController,
) {

   Scaffold {
       padding ->
       Box(
           modifier = Modifier
               .fillMaxSize()
               .padding(padding),
           contentAlignment = Alignment.Center
       ){
           Column(
               modifier = Modifier
                   .fillMaxSize()
                   .padding(16.dp),
               horizontalAlignment = Alignment.CenterHorizontally,
               verticalArrangement = Arrangement.Center
           ) {
               Text(
                   text = "Sign Up",
                   fontSize = 24.sp,
                   fontWeight = FontWeight.Bold,
                   modifier = Modifier.padding(16.dp)
               )

               TextField(
                   value = state.username,
                   onValueChange = {
                      onEvent( UserEvent.SetUserName(it)) },
                   placeholder = { Text("Username") },
                   singleLine = true,
                   modifier = Modifier
                       .fillMaxWidth()
                       .padding(8.dp)
               )
              TextField(
                   value = state.password,
                   onValueChange = {
                       onEvent(UserEvent.SetPassword(it)) },
                   placeholder = { Text("Password") },
                   singleLine = true,
                   visualTransformation = PasswordVisualTransformation(),
                   keyboardOptions = KeyboardOptions.Default.copy(
                       imeAction = ImeAction.Done
                   ),
                  modifier = Modifier
                       .fillMaxWidth()
                       .padding(8.dp)
               )
               Button(
                   onClick = {
                       onEvent(UserEvent.SaveUser)
                       navController.navigate("note"){
                           popUpTo("auth")
                       }
                   },
                   modifier = Modifier
                       .fillMaxWidth()
                       .padding(8.dp)
               ) {
                   Text(text = "Sign Up")
               }

               TextButton(
                   onClick = {
                   navController.navigate("LoginPage") }
               ) {
                   Text(text = "Already Have?")
               }
           }
       }

   }
}
