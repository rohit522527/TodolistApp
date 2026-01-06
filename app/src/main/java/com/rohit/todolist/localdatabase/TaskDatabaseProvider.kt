package com.rohit.todolist.localdatabase

import android.content.Context
import androidx.room.Room

object TaskDatabaseProvider {
    private var INSTANCE: TaskDatabase?=null
    fun getDatabase(context: Context): TaskDatabase{
        return INSTANCE?:synchronized(this){
            val instance=Room.databaseBuilder(
                context.applicationContext,
                TaskDatabase::class.java,
                "taskdb"
            ).fallbackToDestructiveMigration()
                .build()
            INSTANCE=instance
            instance
        }
    }
}