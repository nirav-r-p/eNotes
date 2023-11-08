package com.example.notestaker.components

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp

import com.example.notestaker.localDataBase.userdata.UserInfo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserCard(user: UserInfo, delete:()->Unit, onClick:()->Unit) {

    var expanded by remember {
        mutableStateOf(false)
    }
    var pass by remember {
        mutableStateOf("")
    }
  val context= LocalContext.current
    Column(modifier = Modifier
        .fillMaxWidth()
        .clickable { expanded = !expanded }
        .padding(horizontal = 10.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween

        ){
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Person, contentDescription = "",
                    modifier = Modifier.padding(end = 12.dp)
                )
                Text(text = user.userName,
                    style = MaterialTheme.typography.titleLarge
                )
            }
            IconButton(onClick = { delete() }) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete User")
            }
        }
        if(expanded){
            OutlinedTextField(
                value = pass,
                onValueChange = {
                    pass=it
                },
                keyboardActions = KeyboardActions(onDone = {
                    if(pass==user.password){
                        onClick()
                    }else{
                      Toast.makeText(context,"Incorrect Password",Toast.LENGTH_SHORT).show()
                    }
                },),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Text(text = "Enter Password")
                },
                shape = RoundedCornerShape(12.dp),
            )
        }

    }
}