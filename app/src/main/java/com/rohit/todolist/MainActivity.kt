package com.rohit.todolist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import com.rohit.todolist.localdatabase.TaskDatabaseProvider
import com.rohit.todolist.navigation.AppNavigation
import com.rohit.todolist.ui.theme.TodolistTheme
import com.rohit.todolist.viewmodels.TaskRepository
import com.rohit.todolist.viewmodels.TaskViewModel
import com.rohit.todolist.viewmodels.TaskViewModelFactory

class MainActivity : ComponentActivity() {
    private lateinit var viewModel: TaskViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val db = TaskDatabaseProvider.getDatabase(this)
        val repository = TaskRepository(db.taskDao())

        viewModel = ViewModelProvider(
            this,
            TaskViewModelFactory(repository)
        )[TaskViewModel::class.java]
        setContent {
            TodolistTheme (dynamicColor = false){
                AppNavigation(viewModel)
            }
        }
    }
}