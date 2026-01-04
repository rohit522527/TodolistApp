package com.rohit.todolist.screens

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.Navigator
import com.rohit.todolist.navigation.Routs
import com.rohit.todolist.ui.theme.appFont

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(navController.navigate(Routs.AddTaskScreen)){
                        popUpTo(Routs.HomeScreen)
                    }
                }
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "")
            }
        },
        topBar = {
            TopAppBar(
                title = { Text("TodoList", fontFamily = appFont, fontSize = 32.sp) }
            )
        }
    ) {innerPadding->
        LazyColumn(contentPadding = innerPadding){
            item {

            }
        }
    }
}