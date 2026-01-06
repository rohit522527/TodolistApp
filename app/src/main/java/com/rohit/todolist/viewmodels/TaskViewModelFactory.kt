package com.rohit.todolist.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class TaskViewModelFactory(
    private val repository: TaskRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return TaskViewModel(repository) as T
    }
}
