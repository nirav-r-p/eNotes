package com.example.notestaker.screens.login_signIn_screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.notestaker.R

@RequiresApi(Build.VERSION_CODES.R)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LandingScreens(
    navController: NavController
) {
    val conf=LocalConfiguration.current
    val isDarkMode by remember {
        mutableStateOf(conf.isNightModeActive)
    }
    Scaffold{
        padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(
                    if (isDarkMode) Color.Black else Color.White
                    ),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(4f)
                    .padding(12.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (isDarkMode){
                        Image(
                            painter = painterResource(id = R.drawable.img), contentDescription = "",
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(2f)
                        )
                    }else{
                        Image(
                            painter = painterResource(id = R.drawable.img_1), contentDescription = "",
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(2f)
                        )
                    }
                    Text(
                        text = "Take Notes, Store it in private mode. Login with different accounts.",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        textAlign = TextAlign.Center,
                        fontSize = 18.sp
                    )
                }

            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.TopCenter
            ) {
                Button(
                    onClick = {navController.navigate("SigInPage")},
                    modifier = Modifier.padding(10.dp)
                ) {
                    Text(
                        text = "Start", style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}
