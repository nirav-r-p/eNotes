package com.example.notestaker.screens

import android.view.ViewTreeObserver
import androidx.activity.ComponentActivity
import androidx.annotation.Px
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
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
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.notestaker.ui.theme.HeadingTextStyle
import com.example.notestaker.user_case.NoteEvent
import com.example.notestaker.user_case.NoteState


@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun EditNote(
    state: NoteState,
    onEvent:(NoteEvent)->Unit,
    navController: NavController
) {

    var isKeyboardVisible by remember { mutableStateOf(false) }
    var fabPosition by remember { mutableStateOf(0.dp) }



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
                     .weight(0.5f)
                     .onFocusChanged { focusState ->
                         if (!focusState.hasFocus) {
                             // Keyboard is hidden, reset FAB position
                             fabPosition = 0.dp
                         }else{
                             fabPosition= (-350).dp
                         }
                     },
                 textStyle = HeadingTextStyle,
                 label = {
                     Text(text = "Title")
                 },
             )
         }
      },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                onEvent(NoteEvent.SaveNotes)
                onEvent(NoteEvent.ResetNoteState)
                if(state.title.isBlank() || state.description.isBlank()){

                }
                else{navController.popBackStack()}
            },
                modifier = Modifier.offset(y = fabPosition)) {
                Icon(imageVector = Icons.Default.Check, contentDescription = "Save")
            }
        }
    ) {
        padding->
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(padding)) {
            TextField(
                value = state.description,
                onValueChange ={onEvent(NoteEvent.SetDescription(it))},
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .onFocusChanged { focusState ->
                        isKeyboardVisible = focusState.isFocused
                        if (!isKeyboardVisible) {
                            fabPosition = 0.dp
                        }else{
                            fabPosition= (-350).dp
                        }
                    },
                label = {
                    Text(text = "About")
                },
                placeholder = {
                    Text(text = "Write here .... ")
                },
                keyboardActions = KeyboardActions.Default
            )
        }
    }
}

