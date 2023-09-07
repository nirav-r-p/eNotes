package com.example.notestaker

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.notestaker.user_case.NoteEvent
import com.example.notestaker.user_case.NoteState


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditNote(
    state: NoteState,
    onEvent:(NoteEvent)->Unit,
    navController: NavController
) {
    Scaffold(
      topBar = {
         Row(modifier = Modifier.fillMaxWidth().padding(vertical = 18.dp, horizontal = 10.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
             Text(text = "Edit Note", fontSize = 28.sp)
             IconButton(
                 onClick = {
                     onEvent(NoteEvent.UpdateNote)
                     navController.popBackStack()
                 }) {
                 Icon(imageVector = Icons.Default.Check, contentDescription = "Save")
             }
         }
      }
    ) {
        padding->
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(padding)) {
            TextField(
                value = state.title,
                onValueChange = {onEvent(NoteEvent.SetTitle(it))},
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.5f),
            )
            TextField(
                value = state.description,
                onValueChange ={onEvent(NoteEvent.SetDescription(it))},
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(5f)
            )

        }
    }
}
//
