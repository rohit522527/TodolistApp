package com.rohit.todolist.viewmodels

import com.rohit.todolist.localdatabase.TaskDao
import com.rohit.todolist.localdatabase.TaskEntity

class TaskRepository(private val taskDao: TaskDao) {

    suspend fun insert(task: TaskEntity) {
        taskDao.insertTask(task)
    }

    suspend fun delete(task: TaskEntity) {
        taskDao.deleteTask(task)
    }

    suspend fun update(task: TaskEntity) {
        taskDao.updateTask(task)
    }

    suspend fun getAllTasks(): List<TaskEntity> {
        return taskDao.getAllTasks()
    }
}