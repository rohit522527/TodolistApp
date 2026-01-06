package com.rohit.todolist.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rohit.todolist.localdatabase.TaskEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TaskViewModel(
    private val repository: TaskRepository
) : ViewModel(){
    private val _tasks = MutableStateFlow<List<TaskEntity>>(emptyList())
    val tasks: StateFlow<List<TaskEntity>> = _tasks

    init {
        loadTasks()
    }

    fun loadTasks() {
        viewModelScope.launch {
            _tasks.value = repository.getAllTasks()
        }
    }

    fun addTask(task: TaskEntity) {
        viewModelScope.launch {
            repository.insert(task)
            loadTasks()
        }
    }

    fun deleteTask(task: TaskEntity) {
        viewModelScope.launch {
            repository.delete(task)
            loadTasks()
        }
    }

    fun toggleTask(task: TaskEntity) {
        viewModelScope.launch {
            repository.update(task.copy(isCompleted = !task.isCompleted))
            loadTasks()
        }
    }

}