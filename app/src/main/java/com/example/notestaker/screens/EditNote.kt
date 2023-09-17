package com.example.notestaker.screens

import android.view.ViewTreeObserver
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.LockOpen
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
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

    var isKeyboardVisible by remember { mutableStateOf(false) }
    var fabPosition by remember { mutableStateOf(0.dp) }
    var bottomPadding by remember {
       mutableStateOf(0.dp)
    }
    if (isKeyboardVisible){
        bottomPadding =  340.dp
        fabPosition=(-350).dp
    }else{
        bottomPadding =  0.dp
        fabPosition=0.dp
    }

    // detect keyboard is visible or not.
    val rootView = LocalView.current
    DisposableEffect(rootView) {
        val listener = ViewTreeObserver.OnGlobalLayoutListener {
            val rect = android.graphics.Rect()
            rootView.getWindowVisibleDisplayFrame(rect)
            val screenHeight = rootView.height
            val keypadHeight = screenHeight - rect.bottom
            isKeyboardVisible = keypadHeight > screenHeight * 0.15
        }
        rootView.viewTreeObserver.addOnGlobalLayoutListener(listener)
        onDispose {
            rootView.viewTreeObserver.removeOnGlobalLayoutListener(listener)
        }
    }

    Scaffold(
      topBar = {
         Row(modifier = Modifier
             .fillMaxWidth()
             , verticalAlignment = Alignment.CenterVertically) {
             TextField(
                 value = state.title,
                 onValueChange = {onEvent(NoteEvent.SetTitle(it))},
                 modifier = Modifier
                     .fillMaxWidth()
                     .weight(0.5f),
                 textStyle = MaterialTheme.typography.titleLarge,
                 label = {
                     Text(text = "Title")
                 },
             )
         }
      },
        floatingActionButton = {
            Column {
                FloatingActionButton(
                    onClick = {
                            onEvent(NoteEvent.SetPrivate(status = state.status, id = state.id))
                    },
                    modifier = Modifier
                        .offset(y = fabPosition)
                        .padding(4.dp)
                ) {
                    Icon(
                        imageVector = if(state.status)Icons.Default.Lock else Icons.Default.LockOpen, contentDescription = "Set Private")
                }
                FloatingActionButton(
                    onClick = {
                        onEvent(NoteEvent.SaveNotes)
                        onEvent(NoteEvent.ResetNoteState)
                        if(state.title.isNotBlank() || state.description.isNotBlank()) {
                            navController.popBackStack()
                        }
                    },
                    modifier = Modifier
                        .offset(y = fabPosition)
                        .padding(4.dp)
                ) {
                    Icon(imageVector = Icons.Default.Check, contentDescription = "Save")
                }
            }
        }
    ) {
        padding->
        Box(modifier = Modifier
            .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                TextField(
                    value = state.description,
                    onValueChange = { onEvent(NoteEvent.SetDescription(it)) },
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState(), reverseScrolling = true)
                        .padding(bottom = bottomPadding),
                    label = {
                        Text(text = "About")
                    },
                    placeholder = {
                        Text(text = "Write here .... ")
                    },
                    keyboardActions = KeyboardActions.Default,
                    textStyle = MaterialTheme.typography.bodyMedium
                )

            }
        }
    }
}

