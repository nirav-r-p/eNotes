package com.example.notestaker.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Dehaze
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.notestaker.components.NotesItem
import com.example.notestaker.components.SearchField
import com.example.notestaker.user_case.NoteEvent
import com.example.notestaker.user_case.NoteState


@OptIn(ExperimentalMaterial3Api::class)

@Composable
fun NotesScreen(
    state:NoteState,
    onEvent:(NoteEvent)->Unit,
    navController: NavController
) {
    var isGrid by rememberSaveable {
        mutableStateOf(false)
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                onEvent(NoteEvent.ResetNoteState)
                navController.navigate("EditNote")

            }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Note")
            }
        }
    ) {
        padding->

           Column(modifier = Modifier.fillMaxSize())
           {
               Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                   Text(
                       text = "E-Note",
                       fontSize = 36.sp,
                       fontWeight = FontWeight.SemiBold,
                       modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
                   )
                   IconButton(onClick = {if(state.notes.size>1){ isGrid=!isGrid} }) {
                       Icon(
                           imageVector =
                           when(!isGrid){
                               true ->Icons.Default.Dehaze
                               else->Icons.Default.GridView
                           },

                           contentDescription = ""
                       )
                   }
                   }

               SearchField(state = state, onEvent =onEvent )
               LazyVerticalGrid(
                   columns = GridCells.Fixed(
                       when{
                           isGrid && state.notes.size>1 ->2
                           else ->1
                       }
                   ),
                   modifier=Modifier.fillMaxWidth(),
                   contentPadding = padding,
                   content = {
                       items(state.notes) { note ->
                           NotesItem(note = note, onEvent = onEvent, navController = navController)
                       }
                   }
               )

           }
    }
}