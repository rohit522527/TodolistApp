package com.rohit.todolist.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHost
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.rohit.todolist.screens.AddTaskScreen
import com.rohit.todolist.screens.EditTaskScreen
import com.rohit.todolist.screens.HomeScreen
import com.rohit.todolist.screens.SplashScreen
import com.rohit.todolist.viewmodels.TaskViewModel

@Composable
fun AppNavigation (viewModel: TaskViewModel){
    val navController = rememberNavController()
    NavHost(navController=navController, startDestination = Routs.SplashScreen ){
        composable<Routs.SplashScreen>{
            SplashScreen(navController)
        }
        composable<Routs.HomeScreen>{
            HomeScreen(navController,viewModel)
        }
        composable<Routs.AddTaskScreen> {
            AddTaskScreen(navController,viewModel)
        }
        composable <Routs.EditTaskScreen>{
            EditTaskScreen(navController,viewModel)
        }
    }
}