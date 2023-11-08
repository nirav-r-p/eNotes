package com.example.notestaker.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.example.notestaker.ui.theme.Shape
import com.example.notestaker.user_case.note_case.NoteEvent
import com.example.notestaker.user_case.note_case.NoteState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchField(
    state: NoteState,
    onEvent:(NoteEvent)->Unit
) {
   
    OutlinedTextField(
        value = state.searchNote,
        onValueChange ={
            onEvent(NoteEvent.SetSearchNote(it))
        },
        shape = Shape.small,
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp),
        leadingIcon = {
            IconButton(onClick = {onEvent(NoteEvent.SetSearchNote(state.searchNote))}, modifier = Modifier.padding(4.dp)) {
                Icon(imageVector = Icons.Default.Search, contentDescription = "")
            }
        },
        placeholder = {
            Text(text = "Search")
        },
        maxLines = 1,
        keyboardActions= KeyboardActions(onSearch = {
            onEvent(NoteEvent.SetSearchNote(state.searchNote))
        }),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search)
    )
}