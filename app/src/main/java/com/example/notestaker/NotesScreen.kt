package com.example.notestaker

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.notestaker.ui.theme.Shape
import com.example.notestaker.user_case.NoteEvent
import com.example.notestaker.user_case.NoteState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesScreen(
    state:NoteState,
    onEvent:(NoteEvent)->Unit
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                onEvent(NoteEvent.ShowAddBox)
            }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Note")
            }
        }
    ) {
        padding->
        if(state.isAddingNote){
             AddDialog(state = state, onEvent =onEvent)
        }
            LazyColumn(
                contentPadding = padding,
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(18.dp)

            ) {
                item {
                    OutlinedTextField(
                        value = state.searchNote,
                        onValueChange ={
                        onEvent(NoteEvent.SetSearchNote(it))
                        },
                        shape = Shape.medium,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(25.dp),
                        leadingIcon = {
                            IconButton(onClick = {onEvent(NoteEvent.SearchNote)}, modifier = Modifier.padding(4.dp)) {
                                Icon(imageVector = Icons.Default.Search, contentDescription = "")
                            }
                        },
                        placeholder = {
                            Text(text = "Search")
                        }
                    )
                }
                items(state.notes) { note ->
                    NotesItem(note = note, onEvent = onEvent)
                }
            }

    }
}