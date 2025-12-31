package com.rohit.todolist.navigation

import kotlinx.serialization.Serializable
import kotlinx.serialization.Serializer

sealed class Routs {
    @Serializable
    object SplashScreen: Routs()
    @Serializable
    object HomeScreen: Routs()
}