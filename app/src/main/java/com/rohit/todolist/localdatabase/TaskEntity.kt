package com.rohit.todolist.localdatabase

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "tasks")
data class TaskEntity (
    @PrimaryKey(autoGenerate = true)
    val id :Int=0,
    val title:String,
    val description:String,
    val isCompleted: Boolean,
    val createdAt: Long,
    val priority:Int=1,
    val duoDate: Long
)