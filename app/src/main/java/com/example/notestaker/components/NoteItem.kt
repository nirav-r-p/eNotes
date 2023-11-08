package com.example.notestaker.components


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.notestaker.localDataBase.notedata.Note
import com.example.notestaker.user_case.note_case.NoteEvent

@Composable
fun NotesItem(
    note: Note,
    onEvent: (NoteEvent)->Unit,
    isPrivate:Boolean,
    navController: NavController
) {


    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(horizontal = 9.dp, vertical = 10.dp)
       ) {
        Box (contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()){
            Box(contentAlignment = Alignment.BottomEnd, modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    onEvent(NoteEvent.IsEditMode)
                    onEvent(NoteEvent.SetEditNote(note))
                    if(note.status){
                        navController.navigate("DailLogBox")
                    }else{
                        navController.navigate("EditNote")
                    }

                }
                .blur(
                    if (isPrivate) 12.dp else 0.dp,
                    BlurredEdgeTreatment.Rectangle
                )) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = note.title,
                            style = MaterialTheme.typography.titleLarge,
                            modifier = Modifier
                                .padding(14.dp)
                                .weight(1f)
                        )
                        IconButton(onClick = {
                            if(!isPrivate){
                               onEvent(NoteEvent.DeleteNotes(note))
                            }
                        }) {
                            Icon(imageVector = Icons.Default.Delete, contentDescription = "")
                        }
                    }
                    Text(
                        text = note.description, modifier = Modifier
                            .weight(2f)
                            .padding(14.dp)
                            .fillMaxWidth(),
                        style = MaterialTheme.typography.bodyMedium,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                Text(
                    text = note.editTime,
                    style = MaterialTheme.typography.labelSmall,
                    modifier = Modifier.padding(12.dp)
                )
            }
           if(isPrivate){ Icon(imageVector = Icons.Default.Lock, contentDescription = "Lock")}
        }

    }
}
