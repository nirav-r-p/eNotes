package com.example.notestaker.screens.login_signIn_screens


import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.notestaker.components.UserCard
import com.example.notestaker.user_case.note_case.NoteEvent
import com.example.notestaker.user_case.userLogin.UserEvent
import com.example.notestaker.user_case.userLogin.UserState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SigInScreen(
    state: UserState,
    userEvent: (UserEvent)->Unit,
    noteEvent: (NoteEvent)->Unit,
    navController: NavController,
) {
   var isCreate by remember {
       mutableStateOf(true)
   }
   Scaffold {
       padding ->
         Column(
             modifier = Modifier
                 .fillMaxSize()
                 .padding(padding)
         ) {
             Box(
                 modifier = Modifier
                     .fillMaxWidth()
                     .weight(1.2f)
                     .padding(16.dp)
             ) {
                 Text(
                     text = "Get start with your ideas.💡",
                     style = MaterialTheme.typography.titleLarge,
                     fontSize = 52.sp,
                     lineHeight = 65.sp
                 )
             }
             Box(
                 modifier = Modifier
                     .fillMaxWidth()
                     .weight(2f)
             ) {
                 if (isCreate) {
                     Column(
                         modifier = Modifier.fillMaxSize(),
                         horizontalAlignment = Alignment.CenterHorizontally
                     ) {
                         Text(
                             text = "Create User",
                             modifier = Modifier
                                 .fillMaxWidth()
                                 .padding(15.dp),
                             style = MaterialTheme.typography.titleMedium,
                             fontSize = 24.sp
                         )
                         OutlinedTextField(
                             value = state.username,
                             onValueChange = {
                                 userEvent(UserEvent.SetUserName(it))
                             },
                             placeholder = { Text("Username") },
                             singleLine = true,
                             modifier = Modifier
                                 .fillMaxWidth()
                                 .padding(8.dp),
                             shape = RoundedCornerShape(12.dp)
                         )
                         OutlinedTextField(
                             value = state.password,
                             onValueChange = {
                                 userEvent(UserEvent.SetPassword(it))
                             },
                             placeholder = { Text("Password") },
                             singleLine = true,
                             visualTransformation = PasswordVisualTransformation(),
                             keyboardOptions = KeyboardOptions.Default.copy(
                                 imeAction = ImeAction.Done
                             ),
                             modifier = Modifier
                                 .fillMaxWidth()
                                 .padding(8.dp),
                             shape = RoundedCornerShape(12.dp)
                         )
                         Button(
                             onClick = {
                                 userEvent(UserEvent.SaveUser)
                                 noteEvent(NoteEvent.ClearNoteList)
                                 userEvent(UserEvent.SetUserName(""))
                                 userEvent(UserEvent.SetPassword(""))
                                 isCreate=false

                             },
                             modifier = Modifier
                                 .fillMaxWidth()
                                 .padding(8.dp)
                         ) {
                             Text(text = "Sign Up")
                         }
                         TextButton(
                             onClick = {
                                 isCreate = false
                             }
                         ) {
                             Text(text = "Already Have?")
                         }
                     }
                 }else{
                     LazyColumn(
                         content = {
                             item {
                                 Row(
                                     verticalAlignment = Alignment.CenterVertically,
                                     modifier = Modifier.padding(12.dp)
                                 ) {
                                     IconButton(onClick = { isCreate=true }) {
                                         Icon(imageVector = Icons.Default.ArrowBack, contentDescription ="" )
                                     }
                                     TextButton(
                                         onClick = {
                                             isCreate=true
                                         }
                                     ) {
                                         Text(
                                             text = "back",
                                             style = MaterialTheme.typography.titleMedium,
                                             fontSize = 24.sp
                                         )
                                     }
                                 }

                             }
                             item {
                                 Text(
                                     text = "choose account~",
                                     style = MaterialTheme.typography.titleMedium,
                                     modifier = Modifier.padding(start = 16.dp)
                                 )
                             }
                             items(state.userList){
                                     user->
                                 UserCard(user = user, delete = {userEvent(UserEvent.DeleteUser(user))}) {
                                     userEvent(UserEvent.SetLogin(user.id))
                                     Log.d("User Card", "SigInScreen: User card event $user")
                                     noteEvent(NoteEvent.SetUser(user))
                                     navController.navigate("note"){
                                         popUpTo("auth")
                                     }
                                 }
                             }
                         },
                         modifier = Modifier.fillMaxSize()
                     )
                 }
             }
         }
   }
}
