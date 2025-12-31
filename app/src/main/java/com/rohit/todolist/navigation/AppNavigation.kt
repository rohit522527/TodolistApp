package com.rohit.todolist.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHost
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.rohit.todolist.screens.HomeScreen
import com.rohit.todolist.screens.SplashScreen

@Composable
fun AppNavigation (){
    val navController = rememberNavController()
    NavHost(navController=navController, startDestination = Routs.SplashScreen ){
        composable<Routs.SplashScreen>{
            SplashScreen(navController)
        }
        composable<Routs.HomeScreen>{
            HomeScreen(navController)
        }
    }
}