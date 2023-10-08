package com.example.notestaker.screens.login_signIn_screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.notestaker.user_case.userLogin.UserEvent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LandingScreens(
    navController: NavController
) {
    Scaffold {
        padding ->
        Column(
            modifier = Modifier
            .fillMaxSize()
            .padding(padding),
            verticalArrangement = Arrangement.Bottom
        ) {
            Button(
                onClick = {navController.navigate("SigInPage")},
                modifier = Modifier.padding(15.dp)
            ) {
                Text(
                    text = "Start", style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 12.dp)
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}
