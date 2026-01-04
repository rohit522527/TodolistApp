package com.rohit.todolist.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.rohit.todolist.R
import com.rohit.todolist.navigation.Routs
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavHostController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White),
        contentAlignment = Alignment.Center
    ) {
        Card(

        ) { }
        Image(
            painter = painterResource(id = R.drawable.todolist),
            contentDescription = "",
            modifier = Modifier.background(shape = RoundedCornerShape(12.dp), color = Color(0xff000000))
        )
    }
    LaunchedEffect(Unit) {
        delay(2500)
        navController.navigate(Routs.HomeScreen){
            popUpTo(0)
        }
    }
}