package com.rohit.todolist.localdatabase

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface TaskDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: TaskEntity)

    @Delete
    suspend fun deleteTask(task: TaskEntity)

    @Update
    suspend fun updateTask(task: TaskEntity)

    @Query("select * from tasks order by createdAt desc")
    suspend fun getAllTasks():List<TaskEntity>

    @Query("select * from tasks where isCompleted=0")
    suspend fun getPendingTasks():List<TaskEntity>

}