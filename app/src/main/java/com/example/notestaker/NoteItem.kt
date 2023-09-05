package com.example.notestaker

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row


import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.notestaker.data.Note
import com.example.notestaker.user_case.NoteEvent

@Composable
fun NotesItem(
     note: Note,
     onEvent: (NoteEvent)->Unit
) {
    Card(modifier = Modifier
        .fillMaxWidth()
        .height(200.dp).
            padding(horizontal = 9.dp, vertical = 10.dp)
        .clickable {
//              onEvent(NoteEvent.ShowEditBox(note))
        }) {
       Box(contentAlignment = Alignment.BottomEnd, modifier = Modifier.fillMaxWidth()) {
           Column(modifier = Modifier.fillMaxWidth()) {
               Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically){
                   Text(text =note.title, fontSize = 28.sp, modifier = Modifier
                       .padding(14.dp)
                       .weight(1f))
                   IconButton(onClick = {
                       onEvent(NoteEvent.DeleteNotes(note))
                   }) {
                       Icon(imageVector = Icons.Default.Delete, contentDescription = "")
                   }
               }
               Text(text = note.description, modifier = Modifier
                   .weight(2f)
                   .padding(14.dp)
                   .fillMaxWidth(),
                   fontSize = 12.sp,
                   overflow = TextOverflow.Ellipsis
               )
           }
           Text(text = note.editTime, fontSize = 12.sp, modifier = Modifier.padding(12.dp))
       }

    }
}
//
//@Preview
//@Composable
//fun NotesItemPreview() {
//    NotesItem(note = Note(title = "New Project", description = "Hi jdvbjsbvjsbvjbsvbjbvjsbv", editTime = "23:8:21"),)
//}