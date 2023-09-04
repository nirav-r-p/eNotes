package com.example.notestaker

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.notestaker.user_case.NoteEvent
import com.example.notestaker.user_case.NoteState
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddDialog(
    state: NoteState,
    onEvent: (NoteEvent)->Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        modifier = modifier.height(400.dp),
        onDismissRequest = {
            onEvent(NoteEvent.HideAddBox)
        },
        confirmButton = {
            IconButton(
                onClick = {
                   onEvent(NoteEvent.SaveNotes)
                }
            ) {
                Icon(imageVector = Icons.Default.Check, contentDescription = "Save note")
            } },
        title = {
            OutlinedTextField(
                value = state.title,
                onValueChange ={
                    onEvent(NoteEvent.SetTitle(it))
                } ,
                placeholder = {
                    Text(text = "Title")
                },
                modifier = Modifier.fillMaxWidth()
            ) },
        text = {
            OutlinedTextField(
                value = state.description,
                onValueChange ={
                    onEvent(NoteEvent.SetDescription(it))
                },
                placeholder = {
                    Text(text = "Enter ...")
                },
                modifier= Modifier
                    .fillMaxWidth().fillMaxHeight(),
            )
        },
        dismissButton = {
            TextButton(onClick = { onEvent(NoteEvent.HideAddBox) }) {
                Text(text = "Cancel")
            }
        }
    )

}